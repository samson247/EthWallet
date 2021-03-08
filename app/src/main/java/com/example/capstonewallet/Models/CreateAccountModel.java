package com.example.capstonewallet.Models;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.capstonewallet.AccountRepository;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CreateAccountModel {
    private AccountRepository repository;
    private String address;
    private String name;
    private String fileName;
    private String password;
    String privateKey;
    private static final String DIRECTORY_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS;


    public CreateAccountModel(Context context, String name) {
        repository = new AccountRepository(context);
        this.name = name;
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
            setPassword(password);
            setFileName(path + "/" + fileName);
            insertAccount();
            Log.d("yo123", "account inserted");
            insertName();
            Log.d("yo123", "name inserted");
            insertPassword();
            Log.d("yo123", "password inserted");
        }
        return true;
    }

    public void insertAccount() {
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(password, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        if(credentials != null) {
            setAddress(credentials.getAddress());
            repository.insertAccount(address, fileName);
        }
        else {
            Log.d("yo123", "fail");
        }
    }

    public void addExistingAccount() {
        Credentials credentials = null;
        credentials = Credentials.create(privateKey);
        setAddress(credentials.getAddress());
        insertName();
        insertPassword();
    }

    public void insertName() {
        repository.addContact(address, name);
    }

    public void insertPassword() {
        PasswordModel passwordModel = new PasswordModel();
        String [] passwordRecord = new String[3];
        try {
            passwordRecord = passwordModel.storePassword(password, address);
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        repository.insertPassword(address, passwordRecord[0], passwordRecord[1]);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
