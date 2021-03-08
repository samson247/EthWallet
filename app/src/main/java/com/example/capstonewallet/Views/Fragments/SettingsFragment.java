package com.example.capstonewallet.Views.Fragments;

import android.content.Context;

import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;

import com.example.capstonewallet.Models.AppSharedPreferences;

public class SettingsFragment extends Fragment {
    SharedPreferences sharedpreferences;
    AppSharedPreferences appPreferences;

    public SettingsFragment() {
        sharedpreferences = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        appPreferences = new AppSharedPreferences(sharedpreferences);
    }

    public void setLanguage() {

    }
}
