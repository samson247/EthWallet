package com.example.capstonewallet.viewmodels;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.capstonewallet.Models.ApiServiceAsync;
import com.example.capstonewallet.Models.ApiServiceSync;
import com.example.capstonewallet.Models.Clients.NewsClient;

import java.util.ArrayList;

public class StockNewsViewModel {
    private NewsClient.ArticleData [] articles;
    ArrayList<String> newsText = new ArrayList<>();
    String urlToImage;

    public ArrayList<String> startNewsService() {
        ApiServiceSync apiService = new ApiServiceSync();
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
}
