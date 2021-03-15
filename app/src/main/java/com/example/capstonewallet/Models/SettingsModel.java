package com.example.capstonewallet.Models;

import android.content.SharedPreferences;

import com.example.capstonewallet.viewmodels.SettingsViewModel;

public class SettingsModel {
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int gasPrice;
    private int gasLimit;

    public SettingsModel(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    public void addPreferences() {
        if(!sharedPreferences.contains("gasPrice")) {
            editor.putInt("gasPrice", 4);
        }
        if(!sharedPreferences.contains("gasLimit")) {
            editor.putInt("gasPrice", 21000);
        }
        editor.apply();
    }

    public void editGasPrice() {

    }

    public void editGasLimit() {

    }
}
