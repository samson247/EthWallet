package com.example.capstonewallet.Views.Fragments;

import android.content.Context;

import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;

import com.example.capstonewallet.Models.AppSharedPreferences;
import com.example.capstonewallet.R;
import com.example.capstonewallet.viewmodels.SettingsViewModel;

public class SettingsFragment extends Fragment {
    SharedPreferences sharedPreferences;
    AppSharedPreferences appPreferences;
    SettingsViewModel settingsViewModel;

    public SettingsFragment() {
        //sharedpreferences = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        //appPreferences = new AppSharedPreferences(sharedpreferences);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingsViewModel = new SettingsViewModel(sharedPreferences);
    }

    public void setLanguage() {

    }
}
