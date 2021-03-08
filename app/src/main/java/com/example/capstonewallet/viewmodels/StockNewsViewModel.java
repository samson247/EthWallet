package com.example.capstonewallet.viewmodels;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.capstonewallet.Models.ApiServiceAsync;
import com.example.capstonewallet.Models.ApiServiceSync;
import com.example.capstonewallet.Models.Clients.ChartClient;
import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.Models.StockNewsModel;

import java.util.ArrayList;

public class StockNewsViewModel {
    private NewsClient.ArticleData [] articles;
    ArrayList<String> newsText = new ArrayList<>();
    String urlToImage;
    ArrayList<ChartClient.ChartData> chartData = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();;
    ArrayList<String> dates = new ArrayList<>();
    private StockNewsModel stockNewsModel;
    private ApiServiceSync apiService;
    private String price;

    public StockNewsViewModel() {
        apiService = new ApiServiceSync();
    }

    public ArrayList<String> startNewsService() {
        //apiService = new ApiServiceSync();
        apiService.startNewsClient();
        articles = apiService.getArticles();

        String title;
        //String urlToImage;
        for (int i = 0; i < 19; i++) {
            title = articles[i].getTitle();
            newsText.add(title);

            urlToImage = articles[i].getImageUrl();

            Log.d("yo123", "Url: " + urlToImage);
        }
        return newsText;
    }

    public void startPriceService() {
        apiService.startPriceClient();
        price = apiService.getPrice();
        Log.d("yo123", "price " + price);
    }

    public String getPrice() {
        return this.price;
    }

    public void getChartData() throws Exception {
        ChartClient chartClient = new ChartClient();
        chartData = chartClient.getChartData();
        setChartData();
        //getChartPrices();
        //getChartDates();
    }

    public void setChartData() {
        for(int index = 0; index < chartData.size(); index++) {
            prices.add(chartData.get(index).getValue());
            dates.add(chartData.get(index).getDate());
        }
    }

    public ArrayList<String> getChartPrices() {
        for(int i = 0; i < prices.size(); i++) {
            Log.d("yo123", prices.get(i));
        }
        return this.prices;
    }
    public ArrayList<String> getChartDates() {
        for(int i = 0; i < dates.size(); i++) {
            Log.d("yo123", dates.get(i));
        }
        return this.dates;
    }

    public String convertToUsd(String etherAmount, String etherUnit) {
        stockNewsModel = new StockNewsModel(price);
        String convertedAmount = stockNewsModel.calculateUSDAmount(etherAmount, etherUnit);
        return convertedAmount;
    }
}
