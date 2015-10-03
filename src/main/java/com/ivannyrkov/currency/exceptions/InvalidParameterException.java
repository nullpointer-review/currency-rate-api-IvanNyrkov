package com.ivannyrkov.currency.exceptions;

/**
 * @author Ivan Nyrkov
 */
public class InvalidParameterException extends CurrencyRateException {

    public InvalidParameterException(String message) {
        super(message);
    }

    @Override
    public String getInfo() {
        return getMessage();
    }

}

