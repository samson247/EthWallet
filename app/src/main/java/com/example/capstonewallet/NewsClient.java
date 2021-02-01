package com.example.capstonewallet;

import android.util.Log;

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
    JSONArray obj2;
    String article;
    JSONObject obj3;
    String title;
    String imageUrl;
    ArticleData [] articleData = new ArticleData[20];

    public void run() throws Exception {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("newsapi.org")
                .addPathSegment("v2")
                .addPathSegment("everything")
                .addQueryParameter("q", "ethereum")
                .addQueryParameter("sortBy", "publishedAt")
                .addQueryParameter("apiKey", "a5aa0832af24421db46873860ab12062")
                .build();

        Log.d("yo123", url.toString());
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(String.valueOf(response));

            Log.d("yo123", "gist");

            //Gson gson = new Gson();
            String json = response.body().string();

            obj1 = new JSONObject(json);
            articles = obj1.get("articles").toString();


           // Log.d("yo123", auth);

            //int totalResults = obj1.getInt("totalResults");
           // articles = obj1.get("articles").toString();
            int totalResults = 20;
            Log.d("yo123", String.valueOf(totalResults));
            getArticleData();
            /*for(int i = 0; i < totalResults - 1; i++) {
                obj2 = new JSONArray(articles);
                author = obj2.get(i).toString();
                obj3 = new JSONObject(author);
                auth = obj3.getString("author");

                Log.d("yo123", auth);
            }*/
            //JSONObject obj2 = obj1.getJSONObject("articles");
            //String author = obj2.getString("author");
            //Log.d("yo123", author);

            // ArticleArray art = gson.fromJson(article, ArticleArray.class);


        }
    }

    public void getArticleData() throws JSONException {
        int totalResults = 20;
        Log.d("yo123", String.valueOf(totalResults));

        for(int i = 0; i < totalResults - 1; i++) {
            obj2 = new JSONArray(articles);
            article = obj2.get(i).toString();
            obj3 = new JSONObject(article);
            title = obj3.getString("title");
            imageUrl = obj3.getString("urlToImage");

            Log.d("yo123", title);
            this.articleData[i] = new ArticleData(title, imageUrl);
        }

    }
    public ArticleData [] getArticles() throws Exception {
        Log.d("yo123", "Boutta run");
        this.run();
        return articleData;
    }

    class ArticleData {
        String title;
        String imageUrl;

        ArticleData(String title, String imageUrl) {
            this.title = title;
            this.imageUrl = imageUrl;
        }

        String getTitle() {
            return this.title;
        }
    }
}

