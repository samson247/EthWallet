package com.example.capstonewallet.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstonewallet.R;
import com.example.capstonewallet.Models.WalletModel;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        WalletModel model = new WalletModel(getApplicationContext());
        model.setupBouncyCastle();
        model.createWallet("kingcobra");
    }
}