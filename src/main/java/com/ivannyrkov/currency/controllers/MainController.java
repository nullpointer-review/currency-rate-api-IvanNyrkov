package com.ivannyrkov.currency.controllers;

import com.ivannyrkov.currency.cbr.client.CBRClient;
import com.ivannyrkov.currency.exceptions.CurrencyRateException;
import com.ivannyrkov.currency.exceptions.InvalidDateFormatException;
import com.ivannyrkov.currency.models.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ivan Nyrkov
 */
@RestController
@RequestMapping(value = "/api/rate/{code}")
public class MainController {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateFormat dateFormater = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    private CBRClient cbrClient;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity handleRequestWithoutDate(@PathVariable String code) throws CurrencyRateException {
        CurrencyRate currencyRate = cbrClient.getCurrencyRate(code);
        return new ResponseEntity<>(currencyRate, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{date}")
    public ResponseEntity handleRequestWithDate(@PathVariable String code, @PathVariable String date)
            throws CurrencyRateException {
        CurrencyRate currencyRate = cbrClient.getCurrencyRate(code, parseDate(date));
        return new ResponseEntity<>(currencyRate, HttpStatus.OK);
    }

    @ExceptionHandler(CurrencyRateException.class)
    public ResponseEntity handleError(CurrencyRateException exception) {
        return new ResponseEntity<>(exception.getInfo(), HttpStatus.OK);
    }

    private Date parseDate(String dateString) throws InvalidDateFormatException {
        try {
            return dateFormater.parse(dateString);
        } catch (ParseException e) {
            throw new InvalidDateFormatException(dateString);
        }
    }

}
