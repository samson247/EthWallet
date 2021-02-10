package com.example.capstonewallet;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class AccountEntity implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "fileName")
    public String fileName;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
