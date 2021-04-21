package com.example.capstonewallet.Models.Clients;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceControl;

import com.example.capstonewallet.BuildConfig;

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
    private String address;
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
    String hash;
    ArrayList<TransactionClient.TransactionData> transactionData = new ArrayList<>();

    public TransactionClient(String address) {
        this.address = address;
    }

    public void run() throws Exception {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-rinkeby.etherscan.io")
                .addPathSegment("api")
                .addQueryParameter("module", "account")
                .addQueryParameter("action", "txlist")
                .addQueryParameter("address", address)
                .addQueryParameter("startblock", "0")
                .addQueryParameter("endblock", "99999999")
                .addQueryParameter("sort", "desc")
                .addQueryParameter("apikey", BuildConfig.TX_API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(String.valueOf(response));

            String json = response.body().string();

            transactionResults = new JSONObject(json);
            transactions = transactionResults.get("result").toString();

            int totalResults = 20;
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
            hash = transactionObject.getString("hash");


            //Log.d("yo123", sender);
            this.transactionData.add(new TransactionClient.TransactionData(sender, receiver, time,
                    gas, gasPrice, gasUsed, value, hash));
        }

    }
    public ArrayList<TransactionData> getTransactions() throws Exception {
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
        String hash;

        TransactionData(String sender, String receiver, String time, String gas, String gasPrice,
                        String gasUsed, String value, String hash) {
            this.sender = sender;
            this.receiver = receiver;
            this.time = time;
            this.gas = gas;
            this.gasPrice = gasPrice;
            this.gasUsed = gasUsed;
            this.value = value;
            this.hash = hash;
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

        public String getValue() {
            return this.value;
        }

        public String getGas() {
            return this.gas;
        }

        public String getGasPrice() {
            return this.gasPrice;
        }

        public String getGasUsed() {
            return this.gasUsed;
        }

        public String getHash() { return this.hash; }

        public String[] getTransactionText() {
            if(time != null) {
                Date date = new java.util.Date(Long.parseLong(time) * 1000);
                if(sender.equals(address))
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
            BigDecimal value = null;
            String divisor = "1000000000000000000";
            if(amount.length() < 20) {
                value = BigDecimal.valueOf(Long.parseLong(amount)).divide(BigDecimal.valueOf(Long.parseLong(divisor)));
            }
            else {
                int removal = amount.length() - 19;
                String convertedAmount = amount.substring(0, amount.length() - removal);
                String convertedDivisor = amount.substring(0, divisor.length() - removal);
                value = BigDecimal.valueOf(Long.parseLong(convertedAmount)).divide(BigDecimal.valueOf(Long.parseLong(convertedDivisor)));
            }

            //BigDecimal value = BigDecimal.valueOf(Long.parseLong(amount)).divide(BigDecimal.valueOf(Long.parseLong("1000000000000000000")));
            return value.toString() + " ETH";
        }
    }
}
