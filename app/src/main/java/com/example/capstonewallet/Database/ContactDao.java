package com.example.capstonewallet.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.capstonewallet.Database.ContactEntity;

@Dao
public interface ContactDao {
    @Insert
    void insertContact(ContactEntity a);

    @Delete
    void deleteContact(ContactEntity a);

    @Query("SELECT address FROM contactentity WHERE name LIKE :name")
    String getContact(String name);

    @Query("SELECT * FROM contactentity")
    ContactEntity[] getContacts();
}
