package com.example.capstonewallet.Models;

/**
 * A class to model the StockNews entity and provide its functionality
 *
 * @author Sam Dodson
 */
public class StockNewsModel {
    /**
     * Converts ether value to value in US dollars for an amount specified by user
     * @param usdAmount the amount of USD to find ether value for
     * @param etherValue the value of ether for one USD
     * @return the resulting value of ether for the specified USD amount
     */
    public String calculateUSDAmount(String usdAmount, String etherValue) {
        Integer amount = Integer.parseInt(usdAmount);
        Integer value = Integer.parseInt(etherValue);

        Integer result = amount * value;
        return result.toString();
    }
}
