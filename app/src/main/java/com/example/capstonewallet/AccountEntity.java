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
    @ColumnInfo(name = "publicKey")
    public String publicKey;

    @ColumnInfo(name = "fileName")
    public String fileName;

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
