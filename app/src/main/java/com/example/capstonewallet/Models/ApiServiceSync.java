package com.example.capstonewallet.Models;

import android.util.Log;

import com.example.capstonewallet.Models.Clients.EtherPriceClient;
import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.Models.Clients.TransactionClient;

/**
 * Class to start API services and retrieve data synchronously
 *
 * @author Sam Dodson
 */
public class ApiServiceSync implements ApiService {
    private TransactionClient.TransactionData [] transactionData;
    private NewsClient.ArticleData [] articles;
    String price;

    /**
     * Starts news client and retrieves article data
     */
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

    /**
     * Starts transaction client and retrieves article data
     */
    @Override
    public void startTransactionClient() {

    }

    /**
     * Starts price client and retrieves current ether price
     */
    @Override
    public void startPriceClient() {
        EtherPriceClient client = new EtherPriceClient();

        Thread thread = new Thread()
        {
            public void run() {
                try {
                    price = client.getPrice();
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

    /**
     * Getter for article data
     * @return data associated with ETH news articles
     */
    public NewsClient.ArticleData[] getArticles() {
        return this.articles;
    }

    /**
     * Getter for ether price
     * @return the current price of ether
     */
    public String getPrice() {
        return this.price;
    }
}
