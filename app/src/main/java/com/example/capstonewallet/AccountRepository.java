package com.example.capstonewallet;

import android.content.Context;
//import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import java.io.File;
import java.util.List;

public class AccountRepository {

    private String DB_NAME = "account";

    private AccountDatabase accountDatabase;

    private String accountFile = "";
    private Boolean accountExists = false;

    public AccountRepository(Context context) {
        Log.d("yo123", "in repo constructor");
        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            accountDatabase = Room.databaseBuilder(context, AccountDatabase.class, DB_NAME).build();
            Log.d("yo123", "db not already created");
        }
        else {
            accountDatabase = Room.databaseBuilder(context, AccountDatabase.class, DB_NAME).build();
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

    //public Boolean checkKey(String key) {

    // }

    //public Boolean getAccountExists(String key)
}
