package com.example.capstonewallet.viewmodels;

import android.content.SharedPreferences;

import com.example.capstonewallet.Models.SettingsModel;

/**
 * The view model class for settings fragment. Provides preference
 * data from backend to frontend.
 *
 * @author Sam Dodson
 */
public class SettingsViewModel {
    private String gasPrice;
    private String gasLimit;
    private SettingsModel settingsModel;
    private SharedPreferences sharedPreferences;

    /**
     * Constructor for SettingsViewModel class
     * @param sharedPreferences object used to access app's shared preferences
     */
    public SettingsViewModel(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        settingsModel = new SettingsModel(sharedPreferences);
        settingsModel.addPreferences();
        //gasPrice = settingsModel.getGasPrice();
        //gasLimit = settingsModel.getGasLimit();
    }

    /**
     * A getter for the gasPrice user preference value
     * @return gasPrice the gas price for transactions
     */
    public String getGasPrice() {
        return gasPrice;
    }

    /**
     * A setter for the the gasPrice value
     * @param gasPrice the new gas price for transactions
     */
    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
        settingsModel.editGasPrice(Integer.valueOf(gasPrice));
    }

    /**
     * A getter for the gasLimit user preference value
     * @return gasLimit the gas limit for transactions
     */
    public String getGasLimit() {
        return gasLimit;
    }

    /**
     * A setter for the gasLimit value
     * @param gasLimit the new gas limit for transactions
     */
    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
        settingsModel.editGasLimit(Integer.valueOf(gasLimit));
    }
}
