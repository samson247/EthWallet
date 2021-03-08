package com.example.capstonewallet.Models.Clients;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransactionClient {
    private final OkHttpClient client = new OkHttpClient();
    JSONObject transactionResults;
    String transactions;
    JSONArray transactionArray;
    String transaction;
    JSONObject transactionObject;
    String sender;
    String receiver;
    String time;
    String gas;
    String gasPrice;
    String gasUsed;
    String value;
    ArrayList<TransactionClient.TransactionData> transactionData = new ArrayList<>();

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

            transactionResults = new JSONObject(json);
            transactions = transactionResults.get("result").toString();


            int totalResults = 20;
            Log.d("yo123", String.valueOf(totalResults));
            getTransactionData();
        }
    }

    public void getTransactionData() throws JSONException {
        int totalTransactions = 2;
        Log.d("yo123", String.valueOf(totalTransactions));

        transactionArray = new JSONArray(transactions);

        transactionData.add(new TransactionClient.TransactionData());
        for(int i = 0; i < transactionArray.length(); i++) {
            transaction = transactionArray.get(i).toString();
            transactionObject = new JSONObject(transaction);
            sender = transactionObject.getString("from");
            receiver = transactionObject.getString("to");
            time = transactionObject.getString("timeStamp");
            gas = transactionObject.getString("gas");
            gasPrice = transactionObject.getString("gasPrice");
            gasUsed = transactionObject.getString("gasUsed");
            value = transactionObject.getString("value");


            //Log.d("yo123", sender);
            this.transactionData.add(new TransactionClient.TransactionData(sender, receiver, time,
                    gas, gasPrice, gasUsed, value));
            Log.d("yo123", "Ck" + this.transactionData.get(i).getSender());
            Log.d("yo123", "Ck" + this.transactionData.get(i).getReceiver());
        }

    }
    public ArrayList<TransactionData> getTransactions() throws Exception {
        Log.d("yo123", "Boutta run");
        this.run();
        return transactionData;
    }

    public class TransactionData {
        String sender;
        String receiver;
        String time;
        String gas;
        String gasPrice;
        String gasUsed;
        String value;

        TransactionData(String sender, String receiver, String time, String gas, String gasPrice,
                        String gasUsed, String value) {
            this.sender = sender;
            this.receiver = receiver;
            this.time = time;
            this.gas = gas;
            this.gasPrice = gasPrice;
            this.gasUsed = gasUsed;
            this.value = value;
        }

        TransactionData() {

        }

        public String getSender() {
            return this.sender;
        }

        public String getReceiver() {
            return this.receiver;
        }

        public String getTime() {
            return this.time;
        }

        public String[] getTransactionText() {
            if(time != null) {
                Date date = new java.util.Date(Long.parseLong(time) * 1000);
                if(sender.equals("0x41752a78cf3823dfc5b0969f6545edc1479fd3bb"))
                {
                    //String [] temp = {DateFormat.format("MM.dd.yyyy", date) + " sent     " + convertAmount(this.value) + " ETH"};
                    return new String[]{(String) DateFormat.format("MM.dd.yyyy", date), "sent       ", convertAmount(this.value)};
                }
                else {
                    //String [] temp = {DateFormat.format("MM.dd.yyyy", date) + " received " + convertAmount(this.value) + " ETH"};
                    return new String[]{(String) DateFormat.format("MM.dd.yyyy", date), "received", convertAmount(this.value)};
                }
                //return DateFormat.format("MM.dd.yyyy h:mm a", date) + " received " + this.value;
            }
            //Date date = new java.util.Date(Long.parseLong(time) * 1000);
            //return DateFormat.format("MM.dd.yyyy", date) + " received " + this.value;
            else {
                return new String[]{"Date           ", "Type       ", "Amount"};
            }
        }

        public String convertAmount(String amount) {
            BigDecimal value = BigDecimal.valueOf(Long.parseLong(amount)).divide(BigDecimal.valueOf(Long.parseLong("1000000000000000000")));
            return value.toString() + " ETH";
        }
    }
}
