package com.example.capstonewallet.Models.Clients;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//API key: 1FUCLHFZBYD5MQTU
public class ChartClient {
    //https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=ETH&market=USD&apikey=1FUCLHFZBYD5MQTU

    private final OkHttpClient client = new OkHttpClient();
    JSONObject obj1;
    String result;
    String usdPrice;
    private ArrayList<ChartData> chartData = new ArrayList<>();

    public ArrayList<ChartData> getChartData() throws Exception {
        //https://api.etherscan.io/api?module=stats&action=ethprice&apikey=YourApiKeyToken

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("www.alphavantage.co")
                .addPathSegment("query")
                .addQueryParameter("function", "DIGITAL_CURRENCY_DAILY")
                .addQueryParameter("symbol", "ETH")
                .addQueryParameter("market", "USD")
                .addQueryParameter("apikey", "1FUCLHFZBYD5MQTU")
                .build();

        Log.d("yo123", url.toString());
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(String.valueOf(response));

            Log.d("yo123", "chartclient");
            //Log.d("yo123", response.peekBody(2048).string());
            String json = response.body().string();
            obj1 = new JSONObject(json);
            JSONObject data = obj1.getJSONObject("Time Series (Digital Currency Daily)");
            //Log.d("yo123", "data.keys()");
            int index = 100;
            while(index > 0) {
                Log.d("yo123", data.names().get(index).toString());
                String date = data.getString(data.names().get(index).toString());
                JSONObject weekData = new JSONObject(date);
                String closingValue = weekData.getString("4a. close (USD)");
                Log.d("yo123", closingValue);
                chartData.add(new ChartData(date, closingValue));
                //data.keys().next();
                index -= 1;
            }
        }
        return chartData;
    }

    public class ChartData {
        private String date;
        private String value;

        public ChartData(String date, String value) {
            this.date = date;
            this.value = value;
        }

        public String getDate() {
            return this.date;
        }

        public String getValue() {
            return this.value;
        }
    }
}
