package com.example.capstonewallet.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.capstonewallet.Database.ContactEntity;

/**
 * Data access object for contact table of DB
 */
@Dao
public interface ContactDao {
    @Insert
    void insertContact(ContactEntity a);

    @Delete
    void deleteContact(ContactEntity a);

    // Retrieves address of account based on name
    @Query("SELECT address FROM contactentity WHERE name LIKE :name")
    String getContact(String name);

    // Retrieves name of account based on address
    @Query("SELECT name FROM contactentity WHERE address LIKE :address")
    boolean getName(String address);

    // Retrieves all contacts in DB
    @Query("SELECT * FROM contactentity")
    ContactEntity[] getContacts();
}
