package com.ivannyrkov.currency.cbr.client;

import com.ivannyrkov.currency.exceptions.CurrencyRateException;
import com.ivannyrkov.currency.models.CurrencyRate;

import java.util.Date;

/**
 * @author Ivan Nyrkov
 */
public interface CBRClient {

    CurrencyRate getCurrencyRate(String code, Date date) throws CurrencyRateException;

    CurrencyRate getCurrencyRate(String code) throws CurrencyRateException;

}
