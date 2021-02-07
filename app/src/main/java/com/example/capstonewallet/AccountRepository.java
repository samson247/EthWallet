package com.example.capstonewallet;

import android.content.Context;
//import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;

import java.io.File;
import java.util.List;

public class AccountRepository {

    private String DB_NAME = "account";

    private AccountDatabase accountDatabase;

    private String accountFile = "";
    private Boolean accountExists = false;

    private String address = "";

    public AccountRepository(Context context) {
        Log.d("yo123", "in repo constructor");
        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            accountDatabase = Room.databaseBuilder(context, AccountDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
            Log.d("yo123", "db not already created");
        }
        else {
            accountDatabase = Room.databaseBuilder(context, AccountDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
            Log.d("yo123", "db already created");
        }
        //accountDatabase = Room.databaseBuilder(context, AccountDatabase.class, DB_NAME).build();
    }

    public void closeDatabase() {
        if(accountDatabase.isOpen()) {
            accountDatabase.close();
        }
    }

    public void insertAccount(String publicKey, String accountFile) {
        AccountEntity entity = new AccountEntity();
        entity.setPublicKey(publicKey);
        entity.setFileName(accountFile);
        insertAccount(entity);
    }

    public void insertAccount(AccountEntity entity) {
        Thread t = new Thread(() -> accountDatabase.accountDao().insertAccount(entity));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
    }

    public void deleteAccount(AccountEntity entity) {
        Thread t = new Thread(() -> accountDatabase.accountDao().deleteAccount(entity));
        t.start();
    }

    public void getAccount(String key) {
        Thread t = new Thread()
        {
            public void run() {
                accountFile = accountDatabase.accountDao().getAccount(key);
                Log.d("yo123", "repo " + accountFile);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
    }

    public String getAccountFile(String key) {

        getAccount(key);
        Log.d("yo123", "repo file" + accountFile);

        return accountFile;
    }


    void insertContact(ContactEntity a) {

    }

    void deleteContact(ContactEntity a) {

    }

    private void getContact(String name) {
        Thread t = new Thread()
        {
            public void run() {
                address = accountDatabase.contactDao().getContact(name);
                Log.d("yo123", "repo " + accountFile);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
    }

    public String getContactName(String name) {
        getContact(name);
        return address;
    }

    //public Boolean checkKey(String key) {

    // }

    //public Boolean getAccountExists(String key)
}
