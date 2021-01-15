package com.example.capstonewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WalletView extends AppCompatActivity {
    private WalletViewModel walletViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        // initialize walletviewmodel with intent
    }
}