package com.example.capstonewallet.viewmodels;

import android.util.Log;

import com.braintreepayments.api.dropin.DropInRequest;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.Models.TransactionModel;
import com.example.capstonewallet.databinding.FragmentTransactionBinding;

public class TransactionViewModel {
    private TransactionModel transactionModel;
    private String privateKey;

    public TransactionViewModel(String privateKey) {
        this.privateKey = privateKey;
        transactionModel = new TransactionModel(privateKey);
    }

    public void forwardSendEther(String address, String amount) {
        transactionModel.sendEther(address, amount);
    }

    public void forwardGetEther() {
        //transactionModel.getEther();
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public DropInRequest getDropInRequest() {
        BraintreeClient client = null;
        try {
            client = new BraintreeClient();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DropInRequest dropInRequest = new DropInRequest().clientToken(client.getClientToken());
        Log.d("yo123", "oldway");

        return dropInRequest;
    }
}
