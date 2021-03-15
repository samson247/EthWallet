package com.example.capstonewallet.viewmodels;

import android.content.Intent;
import android.util.Log;

import com.braintreepayments.api.dropin.DropInRequest;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.Models.TransactionModel;
import com.example.capstonewallet.databinding.FragmentTransactionBinding;

public class TransactionViewModel {
    private TransactionModel transactionModel;
    private String privateKey;
    private String balance;
    BraintreeClient client;

    public TransactionViewModel(String privateKey) {
        this.privateKey = privateKey;
        transactionModel = new TransactionModel(privateKey);
        this.balance = transactionModel.getBalanceInEther();
    }

    public void forwardSendEther(String address, String amount) {
        transactionModel.sendEther(address, amount);
    }

    public void forwardGetEther(String amount) {
        transactionModel.getEther(amount);
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public DropInRequest getDropInRequest() {
        client = null;
        try {
            client = new BraintreeClient();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DropInRequest dropInRequest = new DropInRequest().clientToken(client.getClientToken());
        Log.d("yo123", "oldway");

        return dropInRequest;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return this.balance;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, String amount) {
        Log.d("onactres", "here viewmodel");
        Log.d("onactres", "request " + requestCode);
        Log.d("onactres", "result " + resultCode);
        Log.d("onactres", "amount " + amount);
        client.onActivityResult(requestCode, resultCode, data, amount);
    }
}
