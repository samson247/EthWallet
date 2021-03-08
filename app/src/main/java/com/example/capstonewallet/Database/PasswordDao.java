package com.example.capstonewallet.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.capstonewallet.Database.PasswordEntity;

@Dao
public interface PasswordDao {
    @Insert
    void insertPassword(PasswordEntity a);

    @Delete
    void deletePassword(PasswordEntity a);

    @Query("SELECT password FROM passwordentity WHERE address LIKE :address")
     String getPassword(String address);

    @Query("SELECT initVector FROM passwordentity WHERE address LIKE :address")
    String getInitVector(String address);
}
