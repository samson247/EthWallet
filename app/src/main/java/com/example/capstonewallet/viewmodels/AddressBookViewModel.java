package com.example.capstonewallet.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.collection.SparseArrayCompat;

import com.example.capstonewallet.AccountRepository;
import com.example.capstonewallet.Database.ContactEntity;

import java.util.ArrayList;

public class AddressBookViewModel {
    private ArrayList<String> names;
    private ContactEntity[] contacts;
    private AccountRepository repository;

    public AddressBookViewModel(Context context) {
        repository = new AccountRepository(context);
    }

    public ArrayList<String> search(String query) {
        if(names.contains(query)) {

        }
        return names;
    }

    public ArrayList<ContactEntity> getContacts() {
        ContactEntity[] contacts = repository.getContacts();
        ArrayList<ContactEntity> contactList = new ArrayList<>();
        repository.closeDatabase();
        if(contacts.length > 0) {
            int index = 0;
            while(index < contacts.length) {
                if(!(contacts[index].getName() == null))
                {
                    contactList.add(contacts[index]);
                    Log.d("yo123", contacts[index].getName() + " " + contacts[index].getAddress());
                }
                index++;
            }
        }
        //ArrayList<ContactEntity> contactList = new ArrayList<>();
        return contactList;
    }

    public void editContact(String oldName, String oldAddress, String newName, String newAddress) {

    }
}
