package com.example.capstonewallet.Models;

import android.content.SharedPreferences;

public class AppSharedPreferences {
    AppSharedPreferences sharedpreferences;
    private String language = "language";
    SharedPreferences.Editor editor;

    public AppSharedPreferences(SharedPreferences preferences) {
        editor = preferences.edit();
    }

    public void setLanguage(String language) {
        this.editor.putString("language", language);
        this.editor.commit();
    }
}
