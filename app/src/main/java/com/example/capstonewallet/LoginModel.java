package com.example.capstonewallet;

import android.util.Log;

import androidx.lifecycle.LiveData;

public class LoginModel {
    private String publicKey;
    private String password;

    public LoginModel(String publicKey, String password) {
        this.publicKey = publicKey;
        this.password = password;
    }
    public void createAccount(LiveData<String> publicKey, LiveData<String> password) {

    }

    /*public void loginAccount(LiveData<String> publicKey, LiveData<String> password) {
        Log.d("yo123", publicKey.getValue().toString());
        Log.d("yo123", password.getValue().toString());
    }*/

    public void loginAccount(AccountRepository repository) {
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
