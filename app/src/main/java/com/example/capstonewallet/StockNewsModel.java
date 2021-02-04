package com.example.capstonewallet;

public class StockNewsModel {
    public String calculateUSDAmount(String usdAmount, String etherValue) {
        Integer amount = Integer.parseInt(usdAmount);
        Integer value = Integer.parseInt(etherValue);

        Integer result = amount * value;
        return result.toString();
    }
}
