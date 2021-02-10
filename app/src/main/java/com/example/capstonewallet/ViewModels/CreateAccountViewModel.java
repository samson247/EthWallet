package com.example.capstonewallet.ViewModels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.capstonewallet.Models.PasswordModel;
import com.example.capstonewallet.Models.WalletModel;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CreateAccountViewModel {
    private WalletModel walletModel;
    private PasswordModel passwordModel;

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

    private MutableLiveData<String> fileName = new MutableLiveData<String>() {
        @Nullable
        @Override
        public String getValue() {
            return super.getValue();
        }

        @Override
        public void setValue(String fileName) {
            super.setValue(fileName);
        }
    };

    public String getPassword() {
        return this.password.getValue();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public String getFileName() {
        return this.fileName.getValue();
    }

    public void setFileName(String fileName) {
        this.fileName.setValue(fileName);
    }

    public void onClick(Context context) throws IOException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchProviderException, SignatureException, KeyStoreException, IllegalBlockSizeException {
        getInstance(context);

        //work on 2 way binding
        //walletModel.createWallet(binding.editTextTextPersonName2.getText().toString());
        //EditText password = (EditText) v.findViewById(R.id.editTextTextPersonName2);
        //EditText publicKey = (EditText) v.findViewById(R.id.editTextTextPersonName);

        boolean success = walletModel.createWallet(this.getPassword());

        if(success) {
            String [] passwordRecord = passwordModel.storePassword(getPassword(), walletModel.getAddress());
            walletModel.insertPassword(passwordRecord[0], passwordRecord[1], passwordRecord[2]);
        }


        setFileName(walletModel.getFileName());
        Log.d("yo123", "password?" + this.getPassword());
        //popup showing credentials and telling them to take note
    }
}