package com.example.capstonewallet.ViewModels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonewallet.Models.LoginModel;

public class LoginViewModel extends ViewModel {
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

    public boolean onClick(Context context) {
        Log.d("yo123", "pubkey" + this.getPublicKey());
        Log.d("yo123", "password" + this.getPassword());
        LoginModel login = new LoginModel(this.getPublicKey(), this.getPassword(), context);
        boolean loginSuccess = login.loginAccount();
        return loginSuccess;
        // Load next activity
    }
}
