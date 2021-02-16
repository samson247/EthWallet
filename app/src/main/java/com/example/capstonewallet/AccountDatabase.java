package com.example.capstonewallet;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AccountEntity.class, ContactEntity.class, PasswordEntity.class}, version = 6, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();
    public abstract ContactDao contactDao();
    public abstract PasswordDao passwordDao();
}