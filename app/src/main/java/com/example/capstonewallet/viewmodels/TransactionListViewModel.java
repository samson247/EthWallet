package com.example.capstonewallet.viewmodels;

import android.util.Log;

import com.example.capstonewallet.Models.Clients.TransactionClient;

import org.web3j.crypto.Wallet;

import java.util.ArrayList;
import java.util.Collections;

public class TransactionListViewModel {
    ArrayList<TransactionClient.TransactionData> transactionData;
    ArrayList<String[]> transactionText = new ArrayList<>();
    private String address;

    public TransactionListViewModel(String address) {
        this.address = address;
    }

    public ArrayList<TransactionClient.TransactionData> getTransactionText() throws Exception {
        TransactionClient client = new TransactionClient(address);
        transactionData = new ArrayList<>();

        Thread thread = new Thread()
        {
            public void run() {
                try {
                    transactionData = client.getTransactions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();

        return transactionData;
    }

    public ArrayList<String[]> setTransactionText() {
        String sender;
        String [] textToDisplay;
        for(int i = 0; i < transactionData.size(); i++) {
            textToDisplay = transactionData.get(i).getTransactionText();
            transactionText.add(textToDisplay);
        }
        //Collections.reverse(transactionText);
        //Collections.reverse(transactionData);
        return transactionText;
    }

    public ArrayList<TransactionClient.TransactionData> getTransactionData() {
        return transactionData;
    }
}
