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

    public ContactEntity[] getContacts() {
        ContactEntity[] contacts = repository.getContacts();
        repository.closeDatabase();
        if(contacts.length > 0) {
            int i = 0;
            while(i < contacts.length) {
                Log.d("yo123", contacts[i].getName() + " " + contacts[i].getAddress());
                i++;
            }
        }
        return contacts;
    }

}
