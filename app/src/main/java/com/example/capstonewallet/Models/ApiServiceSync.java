package com.example.capstonewallet.Models;

import android.util.Log;

import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.Models.Clients.TransactionClient;

public class ApiServiceSync implements ApiService {
    private TransactionClient.TransactionData [] transactionData;
    private NewsClient.ArticleData [] articles;

    @Override
    public void startNewsClient() {
        Log.d("apiservicesync", "inapisync");
        NewsClient client = new NewsClient();

        Thread thread = new Thread()
        {
            public void run() {
                try {
                    articles = client.getArticles();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startTransactionClient() {

    }

    public NewsClient.ArticleData[] getArticles() {
        return this.articles;
    }
}
