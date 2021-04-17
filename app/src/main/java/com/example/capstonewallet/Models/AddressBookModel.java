package com.example.capstonewallet.Models;

import android.content.Context;
import com.example.capstonewallet.AccountRepository;
import com.example.capstonewallet.Database.ContactEntity;
import java.util.ArrayList;

/**
 * Model class for address book
 */
public class AddressBookModel {
    private Context context;
    private AccountRepository repository;

    /**
     * Constructor for this class
     * @param context used to initialize repository
     */
    public AddressBookModel(Context context) {
        this.repository = new AccountRepository(context);
    }

    /**
     * Gets contacts from repository
     * @return list of contacts
     */
    public ArrayList<ContactEntity> getContacts() {
        openDatabase();
        ContactEntity[] contacts = repository.getContacts();
        closeDatabase();
        ArrayList<ContactEntity> contactList = new ArrayList<>();
        if(contacts.length > 0) {
            int index = 0;
            while(index < contacts.length) {
                if(!(contacts[index].getName() == null))
                {
                    contactList.add(contacts[index]);
                }
                index++;
            }
        }
        return contactList;
    }

    /**
     * Deletes specified contact from Room DB
     * @param name name of contact
     * @param address address of contact
     */
    public void deleteContact(String name, String address) {
        openDatabase();
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setName(name);
        contactEntity.setAddress(address);
        repository.deleteContact(contactEntity);
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
