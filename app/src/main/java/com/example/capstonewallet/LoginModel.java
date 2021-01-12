package com.example.capstonewallet;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

public class LoginModel {
    private String publicKey;
    private String password;
    private AccountRepository repository;

    public LoginModel(String publicKey, String password, Context context) {
        this.publicKey = publicKey;
        this.password = password;
        this.repository = new AccountRepository(context);
    }
    public void createAccount(LiveData<String> publicKey, LiveData<String> password) {

    }

    /*public void loginAccount(LiveData<String> publicKey, LiveData<String> password) {
        Log.d("yo123", publicKey.getValue().toString());
        Log.d("yo123", password.getValue().toString());
    }*/

    public void loginAccount() {
        Log.d("yo123", publicKey);
        Log.d("yo123", password);
        if(repository.getAccountFile(this.publicKey) == null) {
            Log.d("yo123", "inserted");
            repository.insertAccount(this.publicKey, this.password);
        }
        //repository.insertAccount(publicKey.getText().toString(), password.getText().toString());
        String file = repository.getAccountFile(this.publicKey);
        Log.d("yo123", "Done" + file);
    }
}
