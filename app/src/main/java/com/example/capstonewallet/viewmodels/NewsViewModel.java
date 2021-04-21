package com.example.capstonewallet.viewmodels;

import com.example.capstonewallet.Models.ApiServiceSync;
import com.example.capstonewallet.Models.Clients.ChartClient;
import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.Models.NewsModel;
import java.util.ArrayList;

public class NewsViewModel {
    private NewsClient.ArticleData [] articles;
    private ArrayList<ArrayList<String>> articleData = new ArrayList<>();
    private ArrayList<String> newsText = new ArrayList<>();
    private ArrayList<String> url = new ArrayList<>();
    private ArrayList<ChartClient.ChartData> chartData = new ArrayList<>();
    private ArrayList<String> prices = new ArrayList<>();;
    private ArrayList<String> dates = new ArrayList<>();
    private NewsModel newsModel;
    private ApiServiceSync apiService;
    private String price;

    public NewsViewModel() {
        apiService = new ApiServiceSync();
    }

    public ArrayList<ArrayList<String>> startNewsService() {
        apiService.startNewsClient();
        articles = apiService.getArticles();
        parseArticleData(articles);
        return articleData;
    }

    public void parseArticleData(NewsClient.ArticleData [] articles) {
        String title;
        for (int i = 0; i < 19; i++) {
            title = articles[i].getTitle();
            if(!newsText.contains(title)) {
                newsText.add(title);
                url.add(articles[i].getImageUrl());
            }
        }
        articleData.add(newsText);
        articleData.add(url);
    }

    public void startPriceService() {
        apiService.startPriceClient();
        price = apiService.getPrice();
    }

    public ArrayList<ArrayList<String>> getArticleData() {
        return this.articleData;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void getChartData() throws Exception {
        ChartClient chartClient = new ChartClient();
        chartData = chartClient.getChartData();
        setChartData();
    }

    public void setChartData() {
        for(int index = 0; index < chartData.size(); index++) {
            prices.add(chartData.get(index).getValue());
            dates.add(chartData.get(index).getDate());
        }
    }

    public ArrayList<String> getChartPrices() {
        for(int i = 0; i < prices.size(); i++) {
        }
        return this.prices;
    }
    public ArrayList<String> getChartDates() {
        for(int i = 0; i < dates.size(); i++) {
        }
        return this.dates;
    }

    public String convertToUsd(String etherAmount, String etherUnit) {
        newsModel = new NewsModel(price);
        String convertedAmount = newsModel.calculateUSDAmount(etherAmount, etherUnit);
        return convertedAmount;
    }
}
