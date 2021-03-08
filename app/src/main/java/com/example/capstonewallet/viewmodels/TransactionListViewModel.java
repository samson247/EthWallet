package com.example.capstonewallet.viewmodels;

import android.util.Log;

import com.example.capstonewallet.Models.Clients.TransactionClient;

import java.util.ArrayList;

public class TransactionListViewModel {
    ArrayList<TransactionClient.TransactionData> transactionData;
    ArrayList<String[]> transactionText = new ArrayList<>();

    public ArrayList<TransactionClient.TransactionData> getTransactionText() throws Exception {
        TransactionClient client = new TransactionClient();
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
        Log.d("translength", String.valueOf(transactionData.size()));
        for(int i = 0; i < transactionData.size(); i++) {
            sender = "sender" + transactionData.get(i).getSender();
            Log.d("yo123", "sender" + sender);

            textToDisplay = transactionData.get(i).getTransactionText();
            // date = getDate
            // type = getType

            //FIXME pass address and determine if sent or received
            //if(transactionData.get(i).getSender() == )

            transactionText.add(textToDisplay);
        }
        return transactionText;
    }
}
