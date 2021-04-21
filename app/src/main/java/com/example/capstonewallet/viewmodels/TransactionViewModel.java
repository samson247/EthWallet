package com.example.capstonewallet.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;
import com.braintreepayments.api.dropin.DropInRequest;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.Models.TransactionModel;
import com.example.capstonewallet.Views.Fragments.TransactionFragment;
import com.example.capstonewallet.Views.Fragments.TransactionGetFragment;
import com.example.capstonewallet.Views.Fragments.TransactionSendFragment;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

/**
 * View model class for Transaction
 *
 * @author Sam Dodson
 */
public class TransactionViewModel {
    private TransactionModel transactionModel;
    private String balance;
    private BraintreeClient client;
    private final static int FIVE_SECONDS = 5000;
    private boolean transactionInProgress = false;

    /**
     * Constructor for this class
     * @param privateKey the privateKey for this account
     * @param context the context of the fragment
     */
    public TransactionViewModel(String privateKey, Context context, int gasLimit, int gasPrice) {
        transactionModel = new TransactionModel(privateKey, context, gasLimit, gasPrice);
        this.balance = transactionModel.getBalanceInEther();
    }

    /**
     * Calls the model class's method to send ether
     * @param address recipient of ether
     * @param amount the amount of currency to send
     * @param units the unit of currency amount is in
     * @param fragment the fragment to Toast message on
     */
    public void forwardSendEther(String address, String amount, String units, Fragment fragment) {
        Timer timer = new Timer();
        transactionInProgress = true;
        EthSendTransaction ethSendTransaction = transactionModel.sendEther(address, amount, units);
        if(ethSendTransaction == null) {
            ((TransactionSendFragment)fragment).toastOnUIThread("Invalid recipient or amount");
            return;
        }
        Log.d("yo123", "hash: " + ethSendTransaction.getTransactionHash());
        // Checks every five seconds if transaction is complete
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(transactionModel.confirm_transaction(ethSendTransaction)) {
                    timer.cancel();
                    ((TransactionSendFragment)fragment).toastOnUIThread("Transaction successful");
                    transactionInProgress = false;
                }
            }
        };
        timer.schedule(task, FIVE_SECONDS, FIVE_SECONDS);
    }

    /**
     * Calls the model class's method to get ether
     * @param amount the amount of currency to send
     * @param units the unit of currency amount is in
     * @param fragment the fragment to Toast message on
     */
    public void forwardGetEther(String amount, String units, Fragment fragment) {
        Timer timer = new Timer();
        transactionInProgress = true;
        EthSendTransaction ethSendTransaction = transactionModel.getEther(amount, units);
        Log.d("yo123", "hash: " + ethSendTransaction.getTransactionHash());
        // Checks every five seconds if transaction is complete
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(transactionModel.confirm_transaction(ethSendTransaction)) {
                    timer.cancel();
                    ((TransactionGetFragment)fragment).toastOnUIThread("Transaction Successful");
                    transactionInProgress = false;
                }
            }
        };
        timer.schedule(task, FIVE_SECONDS, FIVE_SECONDS);
    }

    /**
     * Gets balance of Ethereum account
     * @return the balance of this account
     */
    public String getBalance() {
        return this.balance;
    }

    public String convertBalance(String newUnit) {
        if(newUnit.equals("Wei")) {
            return transactionModel.getBalance();
        }
        else if(newUnit.equals("ETH")) {
            return transactionModel.getBalanceInEther();
        }
        else {
            return transactionModel.getBalanceInGwei();
        }
    }

    public boolean compareAppBalance(String amount) {
        if(transactionModel.getAppBalance().compareTo(BigDecimal.valueOf(Double.parseDouble(amount))) > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean getTxInProgress() {
        return transactionInProgress;
    }
}
