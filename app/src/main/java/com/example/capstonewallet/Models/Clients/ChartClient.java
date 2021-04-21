package com.example.capstonewallet.Models.Clients;

import android.util.Log;

import com.example.capstonewallet.BuildConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//API key: 1FUCLHFZBYD5MQTU
public class ChartClient {
    private final OkHttpClient client = new OkHttpClient();
    private JSONObject obj1;
    private String result;
    private String usdPrice;
    private ArrayList<ChartData> chartData = new ArrayList<>();

    public ArrayList<ChartData> getChartData() throws Exception {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("www.alphavantage.co")
                .addPathSegment("query")
                .addQueryParameter("function", "DIGITAL_CURRENCY_DAILY")
                .addQueryParameter("symbol", "ETH")
                .addQueryParameter("market", "USD")
                .addQueryParameter("apikey", BuildConfig.CHART_API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(String.valueOf(response));
            String json = response.body().string();
            obj1 = new JSONObject(json);
            JSONObject data = obj1.getJSONObject("Time Series (Digital Currency Daily)");
            int index = 100;
            while(index > 0) {
                String date = data.getString(data.names().get(index).toString());
                JSONObject weekData = new JSONObject(date);
                String closingValue = weekData.getString("4a. close (USD)");
                chartData.add(new ChartData(data.names().get(index).toString(), closingValue));
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
