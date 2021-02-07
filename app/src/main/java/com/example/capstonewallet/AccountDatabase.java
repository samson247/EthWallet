package com.example.capstonewallet;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AccountEntity.class, ContactEntity.class}, version = 2, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();
    public abstract ContactDao contactDao();
}