package com.example.capstonewallet.Models.Clients;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransactionClient {
    private final OkHttpClient client = new OkHttpClient();
    JSONObject obj1;
    String transactions;
    JSONArray obj2;
    String transaction;
    JSONObject obj3;
    String sender;
    String receiver;
    TransactionClient.TransactionData[] transactionData = new TransactionClient.TransactionData[20];

    public void run() throws Exception {
        // https://api-rinkeby.etherscan.io/api?module=account&action=txlist&address=0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a&startblock=0&endblock=99999999&sort=asc&apikey=PK7JGX1HN3H4WS4R4SIMERA75GH9I9F926

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-rinkeby.etherscan.io")
                .addPathSegment("api")
                .addQueryParameter("module", "account")
                .addQueryParameter("action", "txlist")
                .addQueryParameter("address", "0x41752a78cf3823dfc5b0969f6545edc1479fd3bb")
                .addQueryParameter("startblock", "0")
                .addQueryParameter("endblock", "99999999")
                .addQueryParameter("sort", "asc")
                .addQueryParameter("apikey", "PK7JGX1HN3H4WS4R4SIMERA75GH9I9F926")
                .build();

        Log.d("yo123", url.toString());
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(String.valueOf(response));

            Log.d("yo123", "gist");
            Log.d("yo123", response.peekBody(2048).string());
            String json = response.body().string();

            obj1 = new JSONObject(json);
            transactions = obj1.get("result").toString();


            int totalResults = 20;
            Log.d("yo123", String.valueOf(totalResults));
            getTransactionData();
        }
    }

    public void getTransactionData() throws JSONException {
        int totalTransactions = 2;
        Log.d("yo123", String.valueOf(totalTransactions));

        for(int i = 0; i < totalTransactions - 1; i++) {
            obj2 = new JSONArray(transactions);
            transaction = obj2.get(i).toString();
            obj3 = new JSONObject(transaction);
            sender = obj3.getString("from");
            Log.d("yo123", "sender" + sender);
            receiver = obj3.getString("to");
            Log.d("yo123", "receiver" + receiver);

            //Log.d("yo123", sender);
            this.transactionData[i] = new TransactionClient.TransactionData(sender, receiver);
            Log.d("yo123", "Ck" + this.transactionData[i].getSender());
            Log.d("yo123", "Ck" + this.transactionData[i].getReceiver());
        }

    }
    public TransactionClient.TransactionData[] getTransactions() throws Exception {
        Log.d("yo123", "Boutta run");
        this.run();
        return transactionData;
    }

    public class TransactionData {
        String sender;
        String receiver;

        TransactionData(String sender, String receiver) {
            this.sender = sender;
            this.receiver = receiver;
        }

        public String getSender() {
            return this.sender;
        }

        public String getReceiver() {
            return this.receiver;
        }
    }
}
