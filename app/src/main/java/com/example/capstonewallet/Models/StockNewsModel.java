package com.example.capstonewallet.Models;

import android.util.Log;

/**
 * A class to model the StockNews entity and provide its functionality
 *
 * @author Sam Dodson
 */
public class StockNewsModel {
    private String usdValue = "1";

    public StockNewsModel(String usdValue) {
        this.usdValue = usdValue;
    }

    /**
     * Converts ether value to value in US dollars for an amount specified by user
     * @param etherAmount the amount of ether to find value for
     * @return the resulting value of ether for the specified USD amount
     */
    public String calculateUSDAmount(String etherAmount, String etherUnit) {
        Log.d("yo123", "value: " + usdValue);
        Double amount = Double.parseDouble(usdValue);
        Integer value = Integer.parseInt(etherAmount);

        if(etherUnit == "wei") {
            //ethervalue = etherValue / 1000000000000000000;
        }
        else if(etherUnit == "gwei") {
            //ethervalue = etherValue / 1000000000000000
        }

        Double result = amount * value;
        return result.toString();
    }
}
