package com.example.capstonewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        WalletModel model = new WalletModel();
        model.setupBouncyCastle();
        model.createWallet("kingcobra");
    }
}