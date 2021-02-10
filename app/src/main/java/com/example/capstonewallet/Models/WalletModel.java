package com.example.capstonewallet.Models;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.capstonewallet.AccountRepository;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.concurrent.ExecutionException;

public class WalletModel {
    private static final String DIRECTORY_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS;
    private String address;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private int balance;
    private String fileName;
    private AccountRepository repository;
    private Credentials credentials2;

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
        return this.privateKey.toString();
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

    public boolean createWallet(String password)
    {
        this.setupBouncyCastle();
        String path = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
        try {
            fileName = WalletUtils.generateLightNewWalletFile(password, new File(path));
            Log.d("yo123", "Password " + password);
            Log.d("yo123", "Filename " + fileName);
            Log.d("yo123", "Path " + path);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            Log.d("yo123", noSuchAlgorithmException.getMessage());
        } catch (NoSuchProviderException noSuchProviderException) {
            noSuchProviderException.printStackTrace();
            Log.d("yo123", noSuchProviderException.getMessage());
        } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            invalidAlgorithmParameterException.printStackTrace();
            Log.d("yo123", invalidAlgorithmParameterException.getMessage());
        } catch (CipherException cipherException) {
            cipherException.printStackTrace();
            Log.d("yo123", cipherException.getMessage());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            Log.d("yo123", ioException.getMessage());
        }

        if(fileName != null) {
            Log.d("yo123", "filename wasnt null");
            setFileName(path + "/" + fileName);
            loadWalletFromFile(password, fileName, true);
        }
        return true;
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
            // Initialize rest of data
        }
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

    public void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    public void insertPassword(String address, String password, String initVector) {
        //repository.insertPassword(String address, String password, String initVector);
    }
}
