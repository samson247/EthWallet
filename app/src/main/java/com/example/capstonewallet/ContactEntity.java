package com.example.capstonewallet;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ContactEntity implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "name")
    public String name;

    public void setAddress(String Address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }
}
