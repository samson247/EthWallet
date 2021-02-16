package com.example.capstonewallet.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.capstonewallet.Models.WalletModel;

public class CredentialsViewModel {
    private WalletModel model;
    private String fileName;

    private String password;

    public CredentialsViewModel(String password, String fileName) {
        //model = new WalletModel(credentials);
        //setPublicKey(model.getPublicKey());
        //setPrivateKey(model.getPrivateKey());
        //setAddress(model.getAddress());
       // setFileName(model.getFileName());
        setPassword(password);
        setFileName(fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private MutableLiveData<String> publicKey = new MutableLiveData<String>() {
        @Nullable
        @Override
        public String getValue() {
            return super.getValue();
        }

        @Override
        public void setValue(String publicKey) {

            super.setValue(publicKey);
        }
    };

    private MutableLiveData<String> privateKey = new MutableLiveData<String>() {
        @Nullable
        @Override
        public String getValue() {
            return super.getValue();
        }

        @Override
        public void setValue(String privateKey) {

            super.setValue(privateKey);
        }
    };

    private MutableLiveData<String> address = new MutableLiveData<String>() {
        @Nullable
        @Override
        public String getValue() {
            return super.getValue();
        }

        @Override
        public void setValue(String address) {

            super.setValue(address);
        }
    };

    public String getPublicKey() {

        return this.publicKey.getValue();
    }

    public void setPublicKey(String publicKey) {

        this.publicKey.setValue(publicKey);
    }

    public String getPrivateKey() {

        return this.privateKey.getValue();
    }

    public void setPrivateKey(String privateKey) {

        this.privateKey.setValue(privateKey);
    }

    public String getAddress() {

        return this.address.getValue();
    }

    public void setAddress(String address) {

        this.address.setValue(address);
    }
}
