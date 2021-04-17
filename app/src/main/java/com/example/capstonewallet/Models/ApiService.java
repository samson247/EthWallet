package com.example.capstonewallet.Models;

/**
 * Interface to be inherited by asynchronous and synchronous classes that
 * gather various API resources
 */
public interface ApiService {
    public void startNewsClient();
    public void startTransactionClient();
    public void startPriceClient();
}
