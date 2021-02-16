package com.example.capstonewallet;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class PasswordEntity implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "initVector")
    public String initVector;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        Log.d("yo123", "pass " + password);
        this.password = password;
    }

    public void setInitVector(String initVector) {
        Log.d("yo123", "ininit " + initVector);
        this.initVector = initVector;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPassword() {

        return this.password;
    }

    public String getInitVector() {
        return this.initVector;
    }
}
