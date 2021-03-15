package com.example.capstonewallet.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.capstonewallet.Database.AccountEntity;

/**
 * The data access object interface for the account table. It is implemented in AccountRepository.
 *
 * @author Sam Dodson
 */

@Dao
public interface AccountDao {
    @Insert
    void insertAccount(AccountEntity a);

    @Delete
    void deleteAccount(AccountEntity a);

    // Retrieves filename for specified address
    @Query("SELECT fileName FROM accountentity WHERE address LIKE :address")
    String getAccount(String address);

    // Returns the number of accounts associated with an address (should be <= 1)
    @Query("SELECT COUNT(address) FROM accountentity WHERE address = :address")
    int checkAddress(String address);
}