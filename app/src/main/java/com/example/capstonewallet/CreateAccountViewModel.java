package com.example.capstonewallet;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class CreateAccountViewModel {
    private WalletModel walletModel;

    public CreateAccountViewModel getInstance(Context context) {
        //super(R.layout.create_account);
        if(walletModel == null) {
            walletModel = new WalletModel(context);
        }
        return this;
    }

    public WalletModel getWalletModel() {
        return this.walletModel;
    }

    private MutableLiveData<String> password = new MutableLiveData<String>() {
        @Nullable
        @Override
        public String getValue() {
            return super.getValue();
        }

        @Override
        public void setValue(String password) {
            super.setValue(password);
        }
    };

    public String getPassword() {
        return this.password.getValue();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public void onClick(Context context) {
        getInstance(context);

        //work on 2 way binding
        //walletModel.createWallet(binding.editTextTextPersonName2.getText().toString());
        //EditText password = (EditText) v.findViewById(R.id.editTextTextPersonName2);
        //EditText publicKey = (EditText) v.findViewById(R.id.editTextTextPersonName);

        walletModel.createWallet(this.password.toString());
        Log.d("yo123", "password?" + this.password.toString());
        //popup showing credentials and telling them to take note
    }
}