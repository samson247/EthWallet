package com.example.capstonewallet.viewmodels;

import android.content.Context;
import com.example.capstonewallet.Database.ContactEntity;
import com.example.capstonewallet.Models.AddressBookModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * View model class for address book
 *
 * @author Sam Dodson
 */
public class AddressBookViewModel {
    private AddressBookModel addressBookModel;

    /**
     * Constructor for this class
     * @param context context of current state
     */
    public AddressBookViewModel(Context context) {
        addressBookModel = new AddressBookModel(context);

    }

    /**
     * Gets contacts from model class
     * @return list of contacts
     */
    public ArrayList<ContactEntity> getContacts() {
        return addressBookModel.getContacts();
    }

    /**
     * Deletes selected contact from DB
     * @param name name of contact
     * @param address address of contact
     */
    public void deleteContact(String name, String address) {
        addressBookModel.deleteContact(name, address);
    }

    /**
     * Sorts contacts based on name
     */
    public void sortContacts(ArrayList<ContactEntity> contactEntities) {
        Collections.sort(contactEntities, new Comparator<ContactEntity>() {
            @Override
            public int compare(ContactEntity contact1, ContactEntity contact2) {
                return contact1.getName().compareTo(contact2.getName());
            }
        });
    }

    /**
     * Creates sorted list of contacts to add to recycler view
     * @param substring the search query
     * @return list of sorted contacts
     */
    public ArrayList<ContactEntity> setContacts(ArrayList<ContactEntity> contactEntities, String substring) {
        int index = 0;
        ArrayList<ContactEntity> sortedContacts = new ArrayList<>();
        while(index < contactEntities.size()) {
            if(contactEntities.get(index).getName().toLowerCase().contains(substring) || contactEntities.get(index).getName().length() == 1) {
                sortedContacts.add(contactEntities.get(index));
            }
            index++;
        }
        return sortedContacts;
    }
}
