package com.example.capstonewallet.Models;//import android.net.Credentials;
import android.util.Log;

import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import java8.util.Optional;

public class TransactionModel {
    private Web3j web3;
    private Credentials credentials;

    public void connectToEthNetwork() {
        //toastAsync("Connecting to Ethereum network...");
        Log.d("yo123", "sup");
        // FIXME: Add your own API key here
        //web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/8fa740a033224723a9a6bd808bc20e44"));
        web3 = Web3j.build(new HttpService("HTTP://192.168.1.107:7545"));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if(!clientVersion.hasError()){
                //toastAsync("Connected!");
                Log.d("yo123", "connected");
            }
            else {
                //toastAsync(clientVersion.getError().getMessage());
            }
        } catch (Exception e) {
            //toastAsync(e.getMessage());
            Log.d("yo123", e.getMessage());
        }
    }

    public void sendEther(String recipient_address)
    {
        if (credentials == null) {
            credentials = Credentials.create("e2cdbadca25bf5a8a6e79a51ed0f2293a1b25bcba3985d9eebdb6d0f379830b7");
        }
        //credentials = Credentials.create("e2cdbadca25bf5a8a6e79a51ed0f2293a1b25bcba3985d9eebdb6d0f379830b7");
        EthGetTransactionCount ethGetTransactionCount = null;
        try {
            ethGetTransactionCount = web3
                    .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            Log.d("yo123", "tcount");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("yo123", e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("yo123", e.getMessage());
        }
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        // Recipient address
        String recipientAddress = "0x5934a20d487ab4c9e29032122e854b89a27fbae1";
        // Value to transfer (in wei)
        String amountToBeSent= "1";
        BigInteger value = Convert.toWei(amountToBeSent, Convert.Unit.ETHER).toBigInteger(); // Gas Parameter
        BigInteger gasLimit = BigInteger.valueOf(21000);
        BigInteger gasPrice = Convert.toWei("20", Convert.Unit.GWEI).toBigInteger();


        Log.d("yo123", String.valueOf(gasLimit));
        Log.d("yo123", String.valueOf(gasPrice));

        Log.d("yo123", "addy");
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit,
                recipientAddress, value);
        Log.d("yo123", "trans");
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        Log.d("yo123", "signed");
        EthSendTransaction ethSendTransaction = null;
        try {
            ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("yo123", e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("yo123", e.getMessage());
        }

        Log.d("yo123", "sent");
        confirm_transaction(ethSendTransaction);
    }

    // Get transaction receipt
    public void confirm_transaction(EthSendTransaction ethSendTransaction)
    {
        //get TransactionHash
        String transactionHash = null;
        transactionHash = ethSendTransaction.getTransactionHash();
        Log.d("yo123", transactionHash);
        Optional<TransactionReceipt> transactionReceipt = null;
        do {
            System.out.println("checking if transaction “ + transactionHash + “ is mined….");
            EthGetTransactionReceipt ethGetTransactionReceiptResp = null;
            try {
                ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash)
                        .sendAsync().get();
                Log.d("yo123", "waiting");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d("yo123", e.getMessage());
            } catch (ExecutionException e) {
                e.printStackTrace();
                Log.d("yo123", e.getMessage());
            }
            transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
            try {
                Thread.sleep(1); // Wait for 3 sec
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d("yo123", e.getMessage());
            }
        } while (!transactionReceipt.isPresent());


        Log.d("yo123", String.valueOf(transactionReceipt));
    }

    public void get_transactions()
    {

    }
}
