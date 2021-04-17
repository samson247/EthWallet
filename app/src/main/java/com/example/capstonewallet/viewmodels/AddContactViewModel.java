package com.example.capstonewallet.viewmodels;

import android.content.Context;
import com.example.capstonewallet.Models.AddContactModel;

/**
 * View model class for add contact
 *
 * @author Sam Dodson
 */
public class AddContactViewModel {
    private AddContactModel addContactModel;

    /**
     * Constructor for this class
     * @param context context of this class's fragment
     */
    public AddContactViewModel(Context context) {
        addContactModel = new AddContactModel(context);
    }

    /**
     * Calls model class to add contact to DB
     * @param name name of contact to add
     * @param address address of contact to add
     * @return whether contact was added successfully
     */
    public boolean addContact(String name, String address) {
        if(name.length() > 1 && name.length() < 20) {
            addContactModel.addContact(name, address);
            return true;
        }
        return false;
    }

    /**
     * Calls model class to delete stale contact
     * @param name name of stale contact
     * @param address address of stale contact
     */
    public void deleteEditedContact(String name, String address) {
        addContactModel.deleteContact(name, address);
    }

    /**
     * Edits contact and removes stale version of contact
     * @param oldName stale contact name
     * @param oldAddress stale contact address
     * @param newName updated contact name
     * @param newAddress updated contact address
     * @return whether contact was successfully added
     */
    public boolean editContact(String oldName, String oldAddress, String newName, String newAddress) {
        deleteEditedContact(oldName, oldAddress);
        return addContact(newName, newAddress);
    }
}
