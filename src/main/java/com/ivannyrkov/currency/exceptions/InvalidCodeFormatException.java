package com.ivannyrkov.currency.exceptions;

/**
 * @author Ivan Nyrkov
 */
public class InvalidCodeFormatException extends InvalidParameterException {

    private static final String template = "Invalid code format: %s! It should be three-character currency code";

    public InvalidCodeFormatException(String stringCode) {
        super(String.format(template, stringCode));
    }

    @Override
    public String getInfo() {
        return getMessage();
    }
}
