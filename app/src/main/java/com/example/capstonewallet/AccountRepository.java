package com.example.capstonewallet;

import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import com.example.capstonewallet.Database.AccountDatabase;
import com.example.capstonewallet.Database.AccountEntity;
import com.example.capstonewallet.Database.ContactEntity;
import com.example.capstonewallet.Database.PasswordEntity;
import java.io.File;

/**
 * Implements the logic for interacting with Account DB tables
 */
public class AccountRepository {

    private String DB_NAME = "account";

    private final AccountDatabase accountDatabase;

    private String accountFile = "";
    private Boolean accountExists = false;

    public String address = "";
    private String password = "";
    private String initVector = "";
    private ContactEntity[] contacts;

    /**
     * Constructor for AccountRepository class
     * @param context the application context of the class using the DB
     */
    public AccountRepository(Context context) {
        Log.d("yo123", "in repo constructor");
        File dbFile = context.getDatabasePath(DB_NAME);
        // Creates singleton instance of database
        accountDatabase = Room.databaseBuilder(context, AccountDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Clears database of all data
     */
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

    /**
     * Closes database
     */
    public void closeDatabase() {
        if(accountDatabase.isOpen()) {
            accountDatabase.close();
        }
    }

    /**
     * Inserts a new account into database
     * @param address the address of a new Ethereum account
     * @param accountFile the wallet file associated with the account
     */
    public void insertAccount(String address, String accountFile) {
        AccountEntity entity = new AccountEntity();
        entity.setAddress(address);
        entity.setFileName(accountFile);
        insertAccount(entity);
    }

    /**
     * Called by public method to insert account in new thread
     * @param entity the account entity to insert
     */
    private void insertAccount(AccountEntity entity) {
        Thread t = new Thread(() -> accountDatabase.accountDao().insertAccount(entity));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
    }

    /**
     * Deletes an account from the database
     * @param entity the entity to delete from the account
     */
    public void deleteAccount(AccountEntity entity) {
        Thread t = new Thread(() -> accountDatabase.accountDao().deleteAccount(entity));
        t.start();
    }

    /**
     * Retrieves an account from the DB in a separate thread
     * @param address the address to retrieve an account for
     */
    private void getAccount(String address) {
        Thread t = new Thread()
        {
            public void run() {
                Log.d("yo123", "repo " + address);
                accountFile = accountDatabase.accountDao().getAccount(address);
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

    /**
     * Retrieves an account file
     * @param address the address to retrieve an account for
     * @return the account file path
     */
    public String getAccountFile(String address) {

        getAccount(address);
        Log.d("yo123", "repo key " + address);
        Log.d("yo123", "repo file" + accountFile);

        return accountFile;
    }


    /**
     * Inserts contact into Contact table in new thread
     * @param entity the contact entity to insert
     */
    public void insertContact(ContactEntity entity) {
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

    /**
     * Adds contact to Contact table
     * @param address the address to insert into new contact record
     * @param name the name to insert into new contact record
     */
    public void addContact(String address, String name) {
        ContactEntity entity = new ContactEntity();
        entity.setAddress(address);
        entity.setName(name);
        insertContact(entity);
    }

    /**
     * Deletes contact from Contact table in DB
     * @param contactEntity the contact to delete
     */
    public void deleteContact(ContactEntity contactEntity) {
        Thread t = new Thread()
        {
            public void run() {
                accountDatabase.contactDao().deleteContact(contactEntity);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e){
            Log.d("yo123", "error " + String.valueOf(e));
        }
    }

    /**
     * Retrieves contact address based on name in new thread
     * @param name the name to retrieve address for
     */
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

    /**
     * Retrieves contact address based on name
     * @param name the name to retrieve address for
     * @return address associated with contact address
     */
    public String getContactAddress(String name) {
        getContact(name);
        return address;
    }

    /**
     * Retrieves contacts in new thread
     */
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

    /**
     * Retrieves list of all contacts
     * @return the lists of all contacts in table
     */
    public ContactEntity[] getContacts() {
        getContactsHelper();
        return contacts;
    }

    /**
     * Checks to see if address already exists in DB
     * @param address the address to check
     * @return true or false depending on if address belongs to record already
     */
    Boolean checkAddress(String address) {
        Log.d("yo123", "checking");
        boolean exists = false;
        if(accountDatabase.accountDao().checkAddress(address) > 0) {
            Log.d("yo123", "checking2");
            exists = true;
        }
        return exists;
    }

    /**
     * Inserts password into Password table
     * @param address the address associated with password record
     * @param password the password associated with password record
     * @param initVector the initVector associated with password record
     */
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

    /**
     * Inserts password entity into password table
     * @param entity the password entity to insert
     */
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

    /**
     * Retrieves encrypted password based on address
     * @param address the address to retrieve password for
     * @return the encrypted password stored in password table
     */
    public String getPassword(String address) {
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

    /**
     * Retrieves init vector based on address
     * @param address the address to retrieve init vector for
     * @return the initVector of a password
     */
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
