package com.example.capstonewallet;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ContactDao {
    @Insert
    void insertContact(ContactEntity a);

    @Delete
    void deleteContact(ContactEntity a);

    @Query("SELECT address FROM contactentity WHERE name LIKE :name")
    String getContact(String name);
}
