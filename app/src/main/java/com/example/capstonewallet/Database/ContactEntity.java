package com.example.capstonewallet.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Contact table in Room DB, contains address and name columns
 */
@Entity
public class ContactEntity implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "name")
    public String name;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getName() {
        return this.name;
    }
}
