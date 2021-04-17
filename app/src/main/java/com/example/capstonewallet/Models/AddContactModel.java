package com.example.capstonewallet.Models;

import android.content.Context;
import com.example.capstonewallet.AccountRepository;
import com.example.capstonewallet.Database.ContactEntity;

/**
 * Model class for add contact
 *
 * @author Sam Dodson
 */
public class AddContactModel {
    private AccountRepository repository;
    private Context context;

    /**
     * Constructor for this class
     * @param context context of add contact
     */
    public AddContactModel(Context context) {
        this.context = context;
    }

    /**
     * Adds contact to DB
     * @param address address of contact to add
     * @param name name of contact to delete
     */
    public void addContact(String address, String name) {
        openDatabase();
        repository.addContact(address, name);
        closeDatabase();
    }

    /**
     * Deletes contact from DB
     * @param address address of contact to delete
     * @param name name of contact to delete
     */
    public void deleteContact(String address, String name) {
        openDatabase();
        ContactEntity entity = new ContactEntity();
        entity.setAddress(address);
        entity.setName(name);
        repository.deleteContact(entity);
        closeDatabase();
    }

    /**
     * Opens DB if it's currently null
     */
    public void openDatabase() {
        if(repository == null) {
            repository = new AccountRepository(context);
        }
    }

    /**
     * Closes DB
     */
    public void closeDatabase() {
        repository.closeDatabase();
    }
}
