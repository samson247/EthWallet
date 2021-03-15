package com.example.capstonewallet.viewmodels;

import android.content.Context;
import android.util.Log;

import com.example.capstonewallet.AccountRepository;
import com.example.capstonewallet.Database.ContactEntity;

public class AddContactViewModel {
    //private AddContactModel addContactModel;
    private AccountRepository repository;

    public AddContactViewModel(Context context) {
        this.repository = new AccountRepository(context);
    }

    public boolean onClick(String name, String address) {
        if(name.length() > 1 && name.length() < 20) {
            ContactEntity contactEntity = new ContactEntity();
            contactEntity.setName(name);
            contactEntity.setAddress(address);
            repository.insertContact(contactEntity);
            Log.d("yo123", "first contact" + repository.getContactAddress("Test"));

            return true;
        }
        return false;
    }

    public void deleteEditedContact(String name, String address) {
        //repository.deleteContact(address);
    }
}
