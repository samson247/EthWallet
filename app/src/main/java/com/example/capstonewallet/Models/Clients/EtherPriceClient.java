package com.example.capstonewallet.Models.Clients;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EtherPriceClient {
    private final OkHttpClient client = new OkHttpClient();
    JSONObject obj1;
    String result;
    String usdPrice;

    public String getPrice() throws Exception {
        //https://api.etherscan.io/api?module=stats&action=ethprice&apikey=YourApiKeyToken

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.etherscan.io")
                .addPathSegment("api")
                .addQueryParameter("module", "stats")
                .addQueryParameter("action", "ethprice")
                .addQueryParameter("address", "0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a")
                .addQueryParameter("apikey", "PK7JGX1HN3H4WS4R4SIMERA75GH9I9F926")
                .build();

        Log.d("yo123", url.toString());
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(String.valueOf(response));

            Log.d("yo123", "priceclient");
            Log.d("yo123", response.peekBody(2048).string());
            String json = response.body().string();
            obj1 = new JSONObject(json);
            result = obj1.get("result").toString();
            obj1 = new JSONObject(result);
            usdPrice = obj1.get("ethusd").toString();
        }
        return usdPrice;
    }
}
