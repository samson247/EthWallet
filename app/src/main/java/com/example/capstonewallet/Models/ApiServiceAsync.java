package com.example.capstonewallet.Models;

import com.example.capstonewallet.Models.Clients.EtherPriceClient;
import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.Models.Servers.BraintreeServer;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiServiceAsync implements ApiService {
    static final int THREAD_POOL_SIZE = 4;
    private ExecutorService executorService;
    private TransactionClient transactionClient;
    private BraintreeServer braintreeServer;
    private EtherPriceClient etherPriceClient;
    private NewsClient newsClient;
    private ArrayList<TransactionClient.TransactionData> transactionData;
    private NewsClient.ArticleData [] articleData;
    private String token;
    private String etherPrice;

    public ApiServiceAsync() {
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        transactionClient = new TransactionClient();
        braintreeServer = new BraintreeServer();
        etherPriceClient = new EtherPriceClient();
        newsClient = new NewsClient();
    }

    public void startNewsClient() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    articleData = newsClient.getArticles();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startEtherPriceClient() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    etherPrice = etherPriceClient.getPrice();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startTransactionClient() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    transactionData = transactionClient.getTransactions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startPriceClient() {

    }

    public void startBraintreeServer() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    token = braintreeServer.getClientToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startAll() {
        startTransactionClient();
        startEtherPriceClient();
        startNewsClient();
    }

    public ArrayList<TransactionClient.TransactionData> getTransactionData() {
        return this.transactionData;
    }

    public String getToken() {
        return this.token;
    }

    public String getEtherPrice() {
        return this.etherPrice;
    }

    public NewsClient.ArticleData[] getArticleData() {
        return this.articleData;
    }
}
