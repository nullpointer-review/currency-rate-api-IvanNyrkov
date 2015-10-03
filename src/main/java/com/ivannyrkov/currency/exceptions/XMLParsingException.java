package com.ivannyrkov.currency.exceptions;

/**
 * @author Ivan Nyrkov
 */
public class XMLParsingException extends CurrencyRateException {

    public XMLParsingException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getInfo() {
        return getMessage();
    }
}

