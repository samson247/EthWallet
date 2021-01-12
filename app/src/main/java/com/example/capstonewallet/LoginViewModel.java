package com.example.capstonewallet;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public void onClick(Context context) {
        Log.d("yo123", "pubkey" + getPublicKey());
        Log.d("yo123", "password" + getPassword());
        LoginModel login = new LoginModel(getPublicKey(), getPassword(), context);
        login.loginAccount();
        // Load next activity
    }
}
