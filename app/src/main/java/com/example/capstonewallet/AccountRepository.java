package com.example.capstonewallet;

import android.content.Context;
//import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.example.capstonewallet.Database.AccountDatabase;
import com.example.capstonewallet.Database.AccountEntity;
import com.example.capstonewallet.Database.ContactEntity;
import com.example.capstonewallet.Database.PasswordEntity;

import java.io.File;

public class AccountRepository {

    private String DB_NAME = "account";

    private AccountDatabase accountDatabase;

    private String accountFile = "";
    private Boolean accountExists = false;

    public String address = "";
    private String password = "";
    private String initVector = "";
    private ContactEntity[] contacts;

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

    public void clearDatabase() {
        Thread t = new Thread()
        {
            public void run() {
                accountDatabase.clearAllTables();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeDatabase() {
        if(accountDatabase.isOpen()) {
            accountDatabase.close();
        }
    }

    public void insertAccount(String publicKey, String accountFile) {
        AccountEntity entity = new AccountEntity();
        entity.setAddress(publicKey);
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

    private void getAccount(String key) {
        Thread t = new Thread()
        {
            public void run() {
                Log.d("yo123", "repo " + key);
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
        Log.d("yo123", "repo key " + key);
        Log.d("yo123", "repo file" + accountFile);

        return accountFile;
    }


    void insertContact(ContactEntity entity) {
        Log.d("yo123", "in insert contact");
        Log.d("yo123", "address " + entity.getAddress());
        Log.d("yo123", "name " + entity.getName());

        Thread t = new Thread(() -> accountDatabase.contactDao().insertContact(entity));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
    }

    public void addContact(String address, String name) {
        ContactEntity entity = new ContactEntity();
        entity.setAddress(address);
        entity.setName(name);
        insertContact(entity);
    }

    void deleteContact(ContactEntity a) {

    }

    private void getContact(String name) {
        Thread t = new Thread()
        {
            public void run() {
                address = accountDatabase.contactDao().getContact(name);
                Log.d("yo123", "repo " + address);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
    }

    public String getContactAddress(String name) {
        getContact(name);
        return address;
    }

    public void getContactsHelper() {
        Thread t = new Thread()
        {
            public void run() {
                contacts = accountDatabase.contactDao().getContacts();
                Log.d("yo123", "in contacts");
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
    }
    public ContactEntity[] getContacts() {
        getContactsHelper();
        return contacts;
    }

    Boolean checkAddress(String address) {
        Log.d("yo123", "checking");
        boolean exists = false;
        if(accountDatabase.accountDao().checkAddress(address) > 0) {
            Log.d("yo123", "checking2");
            exists = true;
        }
        return exists;
    }

    //public Boolean checkKey(String key) {

    // }

    //public Boolean getAccountExists(String key)

    public void insertPassword(String address, String password, String initVector) {
        PasswordEntity entity = new PasswordEntity();
        entity.setAddress(address);
        entity.setPassword(password);
        entity.setInitVector(initVector);

        Log.d("yo123", "addy " + address);
        Log.d("yo123", "pass " + password);
        Log.d("yo123", "init " + initVector);
        insertPassword(entity);
    }

    private void insertPassword(PasswordEntity entity) {
        Thread t = new Thread(() -> accountDatabase.passwordDao().insertPassword(entity));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
        Log.d("yo123", "password inserted i think");
    }

    void deletePassword(PasswordEntity a) {

    }

    //@Query("SELECT * FROM passwordentity WHERE address LIKE :address")
    public String getPassword(String address) {
        //this.address = address;
        Thread t = new Thread()
        {
            public void run() {
                password = accountDatabase.passwordDao().getPassword(address);
                Log.d("yo123", "pass " + password);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
        return password;
    }

    public String getInitVector(String address) {
        Thread t = new Thread()
        {
            public void run() {
                initVector = accountDatabase.passwordDao().getInitVector(address);
                Log.d("yo123", "init " + initVector);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
        return initVector;
    }
}
