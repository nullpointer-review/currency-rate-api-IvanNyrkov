package com.ivannyrkov.currency.exceptions;

/**
 * @author Ivan Nyrkov
 */
public class InvalidDateFormatException extends InvalidParameterException {

    private static final String template = "Invalid date format: %s! It should be format: yyyy-MM-dd";

    public InvalidDateFormatException(String stringDate) {
        super(String.format(template, stringDate));
    }

    @Override
    public String getInfo() {
        return getMessage();
    }
}
