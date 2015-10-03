package com.ivannyrkov.currency.exceptions;

/**
 * @author Ivan Nyrkov
 */
public class NoDataFoundException extends CurrencyRateException {

    public NoDataFoundException() {
        super("Nothing was found for specified date and currency code");
    }

    @Override
    public String getInfo() {
        return getMessage();
    }
}
