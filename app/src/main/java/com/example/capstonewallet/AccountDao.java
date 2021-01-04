package com.example.capstonewallet;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insertAccount(AccountEntity a);

    @Delete
    void deleteAccount(AccountEntity a);

    @Query("SELECT fileName FROM accountentity WHERE publicKey = :key")
    String getAccount(String key);

    // @Query()
    // Boolean checkKey(String key);
}