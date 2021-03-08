package com.example.capstonewallet.viewmodels;

import java.util.ArrayList;

public class AccountViewModel {
    private String walletName;
    private String password;
    private String address;
    private String privateKey;

    public AccountViewModel(ArrayList<String> credentials) {
        this.walletName = credentials.get(0);
        this.address = credentials.get(1);
        this.privateKey = credentials.get(2);
        this.password = credentials.get(3);
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
