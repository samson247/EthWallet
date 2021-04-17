package com.example.capstonewallet.viewmodels;

import android.content.Context;
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
    private ArrayList<ArrayList<String>> articleData;
    private ArrayList<ArrayList<String>> chartData;
    private String price;

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
        walletModel.loadApiServices();
    }

    public WalletViewModel(Context context, String privateKey) {
        walletModel = new WalletModel(context);
        walletModel.loadExistingWallet(privateKey);
        walletModel.loadApiServices();
    }

    public String getToken() {
        return walletModel.getClientToken();
    }

    public String getEtherPrice() {
        return walletModel.getEtherPrice();
    }

    public ArrayList<TransactionClient.TransactionData> getTransactionData() {
        return walletModel.getTransactionData();
    }

    public NewsClient.ArticleData [] getArticleData() {
        return walletModel.getArticleData();
    }

    public ArrayList<ArrayList<String>> getArticles() {
        return articleData;
    }

    public void setArticles(ArrayList<ArrayList<String>> articleData) {
        this.articleData = articleData;
    }

    public ArrayList<ArrayList<String>> getChartData() {
        return chartData;
    }

    public void setChartData(ArrayList<ArrayList<String>> chartData) {
        this.chartData = chartData;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
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
        ArrayList<String> credentials = new ArrayList<>();
        credentials.add(getWalletName());
        credentials.add(getAddress());
        credentials.add(getPrivateKey());
        credentials.add(getPassword());
        return credentials;
    }
}
