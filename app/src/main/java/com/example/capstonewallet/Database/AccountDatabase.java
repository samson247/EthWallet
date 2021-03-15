package com.example.capstonewallet.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * The Room Database class for the account database.
 *
 * @author Sam Dodson
 */
@Database(entities = {AccountEntity.class, ContactEntity.class, PasswordEntity.class}, version = 6, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {
    // Abstract classes representing the tables in the Room DB
    public abstract AccountDao accountDao();
    public abstract ContactDao contactDao();
    public abstract PasswordDao passwordDao();
}