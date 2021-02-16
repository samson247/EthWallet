package com.example.capstonewallet.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.Models.TransactionModel;
import com.example.capstonewallet.Models.WalletModel;

import java.util.ArrayList;

public class WalletViewModel extends ViewModel {
    private WalletModel walletModel;
    private TransactionModel transactionModel;
    private String fileName;
    private String password;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public WalletViewModel(Context context, String password, String fileName) {
        walletModel = new WalletModel(context);
        setPassword(password);
        setFileName(fileName);
        walletModel.loadWalletFromFile(password, fileName, false);

        String key = walletModel.getPrivateKey();
        transactionModel = new TransactionModel(key);
        transactionModel.getBalance(key);

        //Do something similar to this to set values: setPublicKey(walletModel.getPublicKey());
        walletModel.loadApiServices();
    }

    public String getToken() {
        return walletModel.getClientToken();
    }

    public String getEtherPrice() {
        return walletModel.getEtherPrice();
    }

    public TransactionClient.TransactionData [] getTransactionData() {
        return walletModel.getTransactionData();
    }

    public NewsClient.ArticleData [] getArticleData() {
        return walletModel.getArticleData();
    }

    public String getPrivateKey() {
        return walletModel.getPrivateKey();
    }

    public String getAddress() {
        return walletModel.getAddress();
    }
    public String getWalletName() {
        return walletModel.getWalletName();
    }

    public String getPassword() {
        return walletModel.getPassword();
    }


    public ArrayList<String> getCredentials() {
        int ARRAY_SIZE = 4;
        ArrayList<String> credentials = new ArrayList<>();
        credentials.add(getWalletName());
        credentials.add(getAddress());
        credentials.add(getPrivateKey());
        credentials.add(getPassword());
        return credentials;
    }
}
