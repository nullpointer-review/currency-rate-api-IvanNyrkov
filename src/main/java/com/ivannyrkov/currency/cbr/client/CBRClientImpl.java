package com.ivannyrkov.currency.cbr.client;

import com.ivannyrkov.currency.exceptions.CurrencyRateException;
import com.ivannyrkov.currency.exceptions.InvalidCodeFormatException;
import com.ivannyrkov.currency.exceptions.NoDataFoundException;
import com.ivannyrkov.currency.exceptions.XMLParsingException;
import com.ivannyrkov.currency.models.CurrencyRate;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Ivan Nyrkov
 */
@Component
public class CBRClientImpl implements CBRClient {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=%s";
    private static final String ENCODING = "Windows-1251";

    @Override
    public CurrencyRate getCurrencyRate(String code, Date date) throws CurrencyRateException {
        if (code.length() != 3) {
            throw new InvalidCodeFormatException(code);
        }
        try {
            BigDecimal rateValue = getDailyRate(code, date);
            return new CurrencyRate(code, rateValue, date);
        } catch (IOException e) {
            throw new CurrencyRateException(e);
        }
    }

    @Override
    public CurrencyRate getCurrencyRate(String code) throws CurrencyRateException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        return getCurrencyRate(code, date);
    }

    private BigDecimal getDailyRate(String code, Date date) throws IOException, CurrencyRateException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String requestUrl = String.format(URL, dateFormat.format(date));
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(requestUrl);
        HttpResponse response = client.execute(request);
        try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent(), Charset.forName(ENCODING))) {
            return parseResponseString(code, reader);
        } catch (IOException e) {
            throw new XMLParsingException(e);
        }
    }

    private BigDecimal parseResponseString(String code, Reader xmlReader) throws XMLParsingException, NoDataFoundException {
        Document doc;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(xmlReader);
            doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException(e);
        }

        NodeList elements = doc.getElementsByTagName("Valute");
        if (elements.getLength() == 0) {
            throw new NoDataFoundException();
        }

        for (int i = 0; i < elements.getLength(); i++) {
            Node valuteNode = elements.item(i);
            if (valuteNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) valuteNode;
                String charCode = element.getElementsByTagName("CharCode").item(0).getTextContent();
                if (code.equalsIgnoreCase(charCode)) {
                    String nominalString = element.getElementsByTagName("Nominal").item(0).getTextContent();
                    BigDecimal nominal = new BigDecimal(nominalString);
                    String rateString = element.getElementsByTagName("Value").item(0).getTextContent();
                    BigDecimal result = new BigDecimal(rateString.replace(",", "."));
                    result = result.divide(nominal);
                    return result;
                }
            }
        }
        return null;
    }
}
