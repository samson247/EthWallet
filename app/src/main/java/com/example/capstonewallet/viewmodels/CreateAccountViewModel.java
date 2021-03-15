package com.example.capstonewallet.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.capstonewallet.Models.CreateAccountModel;
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
    private CreateAccountModel createAccountModel;
    private String fileName;
    //private String walletName;

    /*public CreateAccountViewModel getInstance(Context context) {
        //super(R.layout.create_account);
        if(walletModel == null) {
            walletModel = new WalletModel(context);
        }
        return this;
    }*/

    public CreateAccountViewModel getInstance(Context context) {
        //super(R.layout.create_account);
        if(createAccountModel == null) {
            createAccountModel = new CreateAccountModel(context, walletName.getValue());
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

    private MutableLiveData<String> walletName = new MutableLiveData<String>() {
        @Nullable
        @Override
        public String getValue() {
            return super.getValue();
        }

        @Override
        public void setValue(String walletName) {
            super.setValue(walletName);
        }
    };

    public String getPassword() {
        return this.password.getValue();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public String getWalletName() {
        return this.walletName.getValue();
    }

    public void setWalletName(String walletName) {
        this.walletName.setValue(walletName);
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // Move some or all of this logic to LoginModel createAccount
    public int onClick(Context context, boolean addExisting) {
        getInstance(context);

        //work on 2 way binding
        //walletModel.createWallet(binding.editTextTextPersonName2.getText().toString());
        //EditText password = (EditText) v.findViewById(R.id.editTextTextPersonName2);
        //EditText publicKey = (EditText) v.findViewById(R.id.editTextTextPersonName);

        int success = createAccountModel.createWallet(this.getPassword(), this.getWalletName());
        setFileName(createAccountModel.getFileName());


        //Log.d("yo123", "password?" + this.getPassword());
        //popup showing credentials and telling them to take note

        return success;
    }
}