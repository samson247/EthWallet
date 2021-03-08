package com.example.capstonewallet.Models;

import com.example.capstonewallet.AccountRepository;

import java.util.ArrayList;

public class AddressBookModel {
    private ArrayList<Contact> contacts;
    private AccountRepository repository;

    public ArrayList<Contact> getContacts() {
        //repository.getContacts()
        contacts.add(new Contact("name", "address"));
        return contacts;
    }

    public class Contact {
        private String name;
        private String address;

        public Contact(String name, String address) {
            this.name = name;
            this.address = address;
        }
    }
}
