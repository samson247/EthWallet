package com.example.capstonewallet.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.capstonewallet.AccountRepository;
import com.example.capstonewallet.Views.Fragments.TransactionFragment;

import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.crypto.Credentials;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;
import java8.util.Optional;

/**
 * Model class for Transaction
 *
 * @author Sam Dodson
 */
public class TransactionModel {
    private Web3j web3;
    private Credentials credentials;
    private BigInteger value;
    private BigInteger gasLimit;
    private BigInteger gasPrice;
    private AccountRepository repository;

    /**
     * Constructor for this class
     * @param privateKey the private key associated with this account
     */
    public TransactionModel(String privateKey, Context context, int gasLimit, int gasPrice) {
        connectToEthNetwork();
        credentials = Credentials.create(privateKey);
        repository = new AccountRepository(context);
        this.gasLimit = BigInteger.valueOf(gasLimit);
        this.gasPrice = Convert.toWei(String.valueOf(gasPrice), Convert.Unit.GWEI).toBigInteger();;
    }

    /**
     * Setter for credentials used to complete transactions
     * @param privateKey the private key associated with this account that is used to
     * create credentials
     */
    public void setCredentials(String privateKey) {
        credentials = Credentials.create(privateKey);
    }

    /**
     * Connects to Ethereum network (url can be changed to connect to different networks)
     */
    public void connectToEthNetwork() {
        //https://rinkeby.infura.io/v3/8fa740a033224723a9a6bd808bc20e44
        web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/8fa740a033224723a9a6bd808bc20e44"));
        //web3 = Web3j.build(new HttpService("HTTP://192.168.1.107:7545"));
    }

    /**
     * Sends ether to specified account
     * @param recipient the account to send ether to
     * @param amount the amount of ether to send
     */
    public EthSendTransaction sendEther(String recipient, String amount, String units)
    {
        // Converts name to address if necessary
        if(recipient.length() < 20) {
            String name = recipient;
            recipient = convertNameToAddress(name);
        }

        // Converts Wei or Gwei to Ether if necessary
        if(units.equals("Wei")) {
            amount = convertWeiToEther(amount);
        }
        else if(units.equals("Gwei")) {
            amount = convertGweiToEther(amount);
        }

        EthGetTransactionCount transactionCount = null;
        // Gets transaction count for ether account and calculates nonce
        try {
            Log.d("yo123", "block number: " + DefaultBlockParameterName.LATEST);
            transactionCount = web3
                    .ethGetTransactionCount(credentials.getAddress(),
                            DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        BigInteger nonce = transactionCount.getTransactionCount();
        Log.d("yo123", "nonce " + nonce);

        // Sets gas parameters
        setGasParameters(amount);

        // Signs transaction
        byte[] signedTransaction = signTransaction(nonce, recipient, credentials);
        String value = Numeric.toHexString(signedTransaction);
        EthSendTransaction ethSendTransaction = null;

        // Signed transaction is sent
        try {
            ethSendTransaction = web3.ethSendRawTransaction(value).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ethSendTransaction;
    }

    /**
     *
     * @param amount
     */
    public EthSendTransaction getEther(String amount, String units) {
        // TODO Change this to APP wallet account
        //Credentials appCredentials = Credentials.create("d31cc29a589e7eaa906b8c13920fa321b1d4ffbbc23ee4459eeba3e9e5495591"); //credentials;
        if(units.equals("Wei")) {
            amount = convertWeiToEther(amount);
        }
        else if(units.equals("Gwei")) {
            amount = convertGweiToEther(amount);
        }

        Credentials appCredentials = Credentials.create("d31cc29a589e7eaa906b8c13920fa321b1d4ffbbc23ee4459eeba3e9e5495591"); //credentials;
        EthGetTransactionCount ethGetTransactionCount = null;
        try {
            ethGetTransactionCount = web3
                    .ethGetTransactionCount(appCredentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
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
        String recipient = credentials.getAddress();
        // Value to transfer (in wei)
        setGasParameters(amount);
        byte[] signedTransaction = signTransaction(nonce, recipient, appCredentials);

        /*BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        // Gas Parameters
        BigInteger gasLimit = BigInteger.valueOf(21000);
        BigInteger gasPrice = Convert.toWei("20", Convert.Unit.GWEI).toBigInteger();*/


        /*Log.d("yo123", String.valueOf(gasLimit));
        Log.d("yo123", String.valueOf(gasPrice));

        Log.d("yo123", "addy");
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit,
                recipient, value);
        Log.d("yo123", "trans");
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, appCredentials);*/
        String hexValue = Numeric.toHexString(signedTransaction);
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

        return ethSendTransaction;
    }


    /**
     *
     * @param amount
     */
    public void setGasParameters(String amount) {
        value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        Log.d("yo123", "gas: " + gasLimit + " " + gasPrice);
        if(gasLimit == null) {
            gasLimit = BigInteger.valueOf(21000);
        }
        if(gasPrice == null) {
            gasPrice = Convert.toWei("20", Convert.Unit.GWEI).toBigInteger();
        }
    }

    /**
     *
     * @param nonce
     * @param recipient
     * @return
     */
    public byte[] signTransaction(BigInteger nonce, String recipient, Credentials credentials) {
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit,
                recipient, value);
        byte[] signedTransaction = TransactionEncoder.signMessage(rawTransaction, credentials);
        return signedTransaction;
    }

    /**
     * Confirms transaction is successful by getting receipt once transaction is mined
     * @param ethSendTransaction the signed transaction
     * @return if the transaction has been mined and receipt is available
     */
    public boolean confirm_transaction(EthSendTransaction ethSendTransaction)
    {
        String transactionHash = null;
        transactionHash = ethSendTransaction.getTransactionHash();
        Optional<TransactionReceipt> transactionReceipt = null;
        EthGetTransactionReceipt ethGetTransactionReceiptResp = null;
        try {
            ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash)
                    .sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();

        if(transactionReceipt.isPresent()) {
            Log.i("receipt", transactionReceipt.toString());
            return true;
        }
        else {
            Log.i("receipt", "not yet");
            return false;
        }
    }

    /**
     * Gets balance of an account
     * @return the balance of an account
     */
    public String getBalance() {
        EthGetBalance ethGetBalance = null;
        try {
            ethGetBalance = web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //BigDecimal balance = ethGetBalance.getBalance().doubleValue().setScale(2, RoundingMode.HALF_UP);
        BigDecimal balance = BigDecimal.valueOf(ethGetBalance.getBalance().doubleValue());
        balance = balance.setScale(2, RoundingMode.HALF_UP);
        //ethGetBalance.getBalance().doubleValue();
        //return ethGetBalance.getBalance().toString(10);
        return balance.toString();
    }

    /**
     * Gets balance in ether
     * @return the balance of an account in ether
     */
    public String getBalanceInEther() {
        return convertWeiToEther(getBalance());
    }

    /**
     * Gets balance in Gwei
     * @return the balance of account in Gwei
     */
    public String getBalanceInGwei() {
        return Convert.fromWei(getBalance(), Convert.Unit.GWEI).toString();
    }

    /**
     * Converts wei amount to ether
     * @param amount the amount of ether to convert
     * @return the converted amount in ether
     */
    public String convertWeiToEther(String amount) {
        return Convert.fromWei(amount, Convert.Unit.ETHER).toString();
    }

    /**
     * Converts gwei amount to ether
     * @param amount the amount of ether to convert
     * @return the converted amount in ether
     */
    public String convertGweiToEther(String amount) {
        String divisor = "1000000000";
        BigDecimal value = BigDecimal.valueOf(Long.parseLong(amount)).divide(BigDecimal.valueOf(Long.parseLong(divisor)));
        return value.toString();
    }

    /**
     * Converts name to address
     * @param name the contact name to lookup
     * @return the address for the specified name
     */
    private String convertNameToAddress(String name) {
        //TODO error handling
        return repository.getContactAddress(name);
    }
}
