package com.ivannyrkov.currency.exceptions;

/**
 * @author Ivan Nyrkov
 */
public class CurrencyRateException extends Exception {

    public CurrencyRateException() {
    }

    public CurrencyRateException(String message) {
        super(message);
    }

    public CurrencyRateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyRateException(Throwable cause) {
        super(cause);
    }

    public CurrencyRateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getInfo() {
        return "Exception while trying to get currency rate";
    }
}
