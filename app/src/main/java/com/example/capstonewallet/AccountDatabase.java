package com.example.capstonewallet;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AccountEntity.class}, version = 1, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();
}