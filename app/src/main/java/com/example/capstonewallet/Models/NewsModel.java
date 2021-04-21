package com.example.capstonewallet.Models;

import android.util.Log;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * A class to model the StockNews entity and provide its functionality
 *
 * @author Sam Dodson
 */
public class NewsModel {
    private String usdValue = "1";

    public NewsModel(String usdValue) {
        this.usdValue = usdValue;
    }

    /**
     * Converts ether value to value in US dollars for an amount specified by user
     * @param etherAmount the amount of ether to find value for
     * @return the resulting value of ether for the specified USD amount
     */
    public String calculateUSDAmount(String etherAmount, String etherUnit) {
        Double amount = Double.parseDouble(usdValue);
        Double value = Double.parseDouble(etherAmount);
        BigDecimal convertedValue = null;
        BigDecimal result;
        if(etherUnit.equals("Wei")) {

            convertedValue = BigDecimal.valueOf(value)
                    .divide(BigDecimal.valueOf(Long.parseLong("1000000000000000000")));

            result = convertedValue.multiply(BigDecimal.valueOf(amount));
        }
        else if(etherUnit.equals("Gwei")) {
            convertedValue = BigDecimal.valueOf(value)
                    .divide(BigDecimal.valueOf(Long.parseLong("1000000000000000")));

            result = convertedValue.multiply(BigDecimal.valueOf(amount));
        }
        else {
            result = BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(amount));
        }
        result = result.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        if(result.compareTo(BigDecimal.ZERO) == 0) {
            return "< 0.00";
        }
        else {
            return result.toString();
        }
    }
}
