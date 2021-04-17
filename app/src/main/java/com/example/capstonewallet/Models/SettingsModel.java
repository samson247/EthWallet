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
    }

    public void addPreferences() {
        editor = sharedPreferences.edit();
        if(!sharedPreferences.contains("gasPrice")) {
            editor.putInt("gasPrice", 20);
        }
        if(!sharedPreferences.contains("gasLimit")) {
            editor.putInt("gasLimit", 21000);
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                editor.commit();
            }
        });
        thread.run();
    }

    public void editGasPrice(int value) {
        editor = sharedPreferences.edit();
        editor.putInt("gasPrice", value);
        editor.apply();
    }

    public void editGasLimit(int value) {
        editor = sharedPreferences.edit();
        editor.putInt("gasLimit", value);
        editor.apply();
    }
}
