package com.example.capstonewallet.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonewallet.Models.LoginModel;

/**
 * The view model class for the login activity
 */
public class LoginViewModel extends ViewModel {
    private LoginModel loginModel;
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

    public String getAddress() {
        return this.address.getValue();
    }

    public void setAddress(String address) {
        this.address.setValue(address);
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

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean onClick(Context context) {
        loginModel = new LoginModel(this.getAddress(), this.getPassword(), context);
        boolean loginSuccess = loginModel.loginAccount();
        fileName = loginModel.getFileName();
        return loginSuccess;
    }
}
