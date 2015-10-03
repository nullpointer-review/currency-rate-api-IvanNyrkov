package com.ivannyrkov.currency.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ivannyrkov.currency.utils.JsonDateSerializer;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Ivan Nyrkov
 */
@JsonAutoDetect
public class CurrencyRate {

    private String code;

    private BigDecimal rate;

    @JsonSerialize(using = JsonDateSerializer.class)
    private Date date;

    public CurrencyRate() {

    }

    public CurrencyRate(String code, BigDecimal rate, Date date) {
        this.code = code;
        this.rate = rate;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRate that = (CurrencyRate) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        return !(date != null ? !date.equals(that.date) : that.date != null);

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
