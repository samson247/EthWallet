package com.example.capstonewallet;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class CredentialsViewModel {
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

    public String getPublicKey() {

        return this.publicKey.getValue();
    }

    public void setPublicKey(String publicKey) {

        this.publicKey.setValue(publicKey);
    }
}
