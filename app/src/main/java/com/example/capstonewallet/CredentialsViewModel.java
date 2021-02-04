package com.example.capstonewallet;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class CredentialsViewModel {
    private WalletModel model;

    public CredentialsViewModel(String [] credentials) {
        model = new WalletModel(credentials);
        setPublicKey(model.getPublicKey());
        setPrivateKey(model.getPrivateKey());
        setAddress(model.getAddress());

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
