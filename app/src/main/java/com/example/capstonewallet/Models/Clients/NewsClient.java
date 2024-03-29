package com.example.capstonewallet.Models.Clients;

import android.util.Log;

import com.example.capstonewallet.BuildConfig;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsClient {
    private final OkHttpClient client = new OkHttpClient();
    JSONObject obj1;
    String articles;
    JSONArray articlesJson;
    String article;
    JSONObject articleJson;
    String title;
    String url;
    ArticleData [] articleData = new ArticleData[20];

    public void run() throws Exception {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("newsapi.org")
                .addPathSegment("v2")
                .addPathSegment("everything")
                .addQueryParameter("q", "ethereum")
                .addQueryParameter("sortBy", "publishedAt")
                .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(String.valueOf(response));
            String json = response.body().string();

            obj1 = new JSONObject(json);
            articles = obj1.get("articles").toString();


            int totalResults = 20;
            getArticleData();
        }
    }

    public void getArticleData() throws JSONException {
        int totalResults = 20;

        for(int i = 0; i < totalResults - 1; i++) {
            articlesJson = new JSONArray(articles);
            article = articlesJson.get(i).toString();
            articleJson = new JSONObject(article);
            title = articleJson.getString("title");
            url = articleJson.getString("url");
            this.articleData[i] = new ArticleData(title, url);
        }

    }
    public ArticleData [] getArticles() throws Exception {
        this.run();
        return articleData;
    }

    public class ArticleData {
        String title;
        String url;

        ArticleData(String title, String imageUrl) {
            this.title = title;
            this.url = imageUrl;
        }

        public String getTitle() {
            return this.title;
        }

        public String getImageUrl() {
            return this.url;
        }
    }
}

