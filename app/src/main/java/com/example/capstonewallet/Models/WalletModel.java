package com.example.capstonewallet.Models;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.example.capstonewallet.AccountRepository;
import com.example.capstonewallet.Database.ContactEntity;
import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.Models.Clients.TransactionClient;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class WalletModel {
    private static final String DIRECTORY_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS;
    private String address;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private int balance;
    private String fileName;
    private AccountRepository repository;
    private Credentials credentials2;
    private String clientToken;
    private String etherPrice;
    private ArrayList<TransactionClient.TransactionData> transactionData;
    private NewsClient.ArticleData [] articleData;

    public WalletModel(Context context) {
        repository = new AccountRepository(context);
    }

    public WalletModel(Credentials credentials) {

    }

    public WalletModel(String [] credentials) {
        Log.d("yo123", "model" + credentials[0]);
        Log.d("yo123", credentials[0]);
        loadWalletFromFile(credentials[0], credentials[1], false);
    }

    public void setPublicKey(BigInteger publicKey) {

        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return this.publicKey.toString();
    }

    public void setPrivateKey(BigInteger privateKey) {

        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return this.privateKey.toString(16);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // Here or in login?
    public void loadWalletFromFile(String password, String walletFilePath, Boolean justCreated)
    {
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(password, walletFilePath);
        } catch (IOException e) {
            Log.d("yo123", e.getMessage());
        } catch (CipherException e) {
            e.printStackTrace();
            Log.d("yo123", e.getMessage());
        }

        if (credentials != null) {
            Log.i("yo123", "Credentials loaded");
            Log.i("yo123", credentials.getAddress());
            Log.d("yo123", credentials.getEcKeyPair().getPublicKey().toString(16));
            setPublicKey(credentials.getEcKeyPair().getPublicKey());

            if(justCreated == true) {
                setPublicKey(credentials.getEcKeyPair().getPublicKey());
                Log.d("yo123", "Login " + credentials.getAddress());
                Log.d("yo123", "FN:" + fileName);
                repository.insertAccount(credentials.getAddress(), fileName);
            }

            setPublicKey(credentials.getEcKeyPair().getPublicKey());
            setPrivateKey(credentials.getEcKeyPair().getPrivateKey());
            setAddress(credentials.getAddress());
            Log.d("privateKey", "key: " + credentials.getEcKeyPair().getPrivateKey().toString());
            Log.d("privateKey", "key16: " + credentials.getEcKeyPair().getPrivateKey().toString(16));
            // Initialize rest of data
        }
    }

    public void loadExistingWallet(String privateKey) {
        Credentials credentials = Credentials.create(privateKey);
        setAddress(credentials.getAddress());
        setPrivateKey(credentials.getEcKeyPair().getPrivateKey());
    }

    public EthGetBalance getBalance(Web3j web3, EthGetBalance ethGetBalance, Credentials credentials)
    {
        try {
            ethGetBalance = web3
                    .ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                    .sendAsync()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("yo123", e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("yo123", e.getMessage());
        }
        return ethGetBalance;
    }

    public void loadApiServices() {
        ApiServiceAsync service = new ApiServiceAsync(address);
        service.startAll();

        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //clientToken = service.getToken();
                etherPrice = service.getEtherPrice();
                //transactionData = service.getTransactionData();
                articleData = service.getArticleData();

                Log.d("yo123", "time" + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                //Log.d("yo123", "client token" + getClientToken());
                //Log.d("yo123", "ether price" + getEtherPrice());
                Log.d("yo123", "articles" + getArticleData());
                //Log.d("yo123", "transactions" + getTransactionData());

            }
        };
        timer.start();
    }

    public String getClientToken() {
        return this.clientToken;
    }

    public String getEtherPrice() {
        return this.etherPrice;
    }

    public NewsClient.ArticleData [] getArticleData() {
        return this.articleData;
    }

    public ArrayList<TransactionClient.TransactionData> getTransactionData() {
        return this.transactionData;
    }

    public String getWalletName() {
        ContactEntity [] contacts = repository.getContacts();
        for(ContactEntity contact: contacts) {
            if(contact.getAddress().equals(address)) {
                return contact.getName();
            }
        }
        return "Temp Name";
    }

    public String getPassword() {
        String encryptedPassword = repository.getPassword(this.getAddress());
        PasswordModel passwordModel = new PasswordModel();
        String initVector = repository.getInitVector(getAddress());
        String password = null;

        password = passwordModel.loadPassword(getAddress(), Base64.decode(encryptedPassword, Base64.DEFAULT),
                    Base64.decode(initVector, Base64.DEFAULT));

        return password;
    }
}
