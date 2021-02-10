package com.example.capstonewallet.Models;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.capstonewallet.AccountRepository;

public class LoginModel {
    private String address;
    private String password;
    private AccountRepository repository;

    public LoginModel(String address, String password, Context context) {
        this.address = address;
        this.password = password;
        this.repository = new AccountRepository(context);
    }
    public void createAccount(LiveData<String> publicKey, LiveData<String> password) {

    }

    /*public void loginAccount(LiveData<String> publicKey, LiveData<String> password) {
        Log.d("yo123", publicKey.getValue().toString());
        Log.d("yo123", password.getValue().toString());
    }*/

    public boolean loginAccount() {
        boolean loginSuccess = true;
        Log.d("yo123", "address: " + address);
        Log.d("yo123", "password: " + password);
        // Fix this
        /*if(repository.getAccountFile(this.publicKey) == null) {
            Log.d("yo123", "inserted");
            repository.insertAccount(this.publicKey, this.password);
        }*/
        //repository.insertAccount(publicKey.getText().toString(), password.getText().toString());
        int MAX_CONTACT_LENGTH = 20;
        if(this.address.length() < MAX_CONTACT_LENGTH) {
            this.address = repository.getContactName(this.address);
            // verify address isn't null
            // Create int codes and return instead of boolean
        }
        String file = repository.getAccountFile(this.address);
        if(file == null) {
            Log.d("yo123", "Wrong address");
            loginSuccess = false;
        }

        Log.d("yo123", "Done" + file);

        repository.closeDatabase();
        return loginSuccess;
    }
}
