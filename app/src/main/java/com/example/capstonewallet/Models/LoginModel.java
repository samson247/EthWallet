package com.example.capstonewallet.Models;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.example.capstonewallet.AccountRepository;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * A class to model the login functionality of this app. Provides access to ROOM database to
 * validate login to Ethereum wallet account.
 *
 * @author Sam Dodson
 *
 */
public class LoginModel {
    private String address;
    private String password;
    private String fileName;
    private AccountRepository repository;

    /**
     * Argumented constructor for the LoginModel class
     *
     * @param address the address of the Ethereum account to login to
     * @param password the password corresponding to the wallet account
     * @param context the context of the login activity
     */
    public LoginModel(String address, String password, Context context) {
        this.address = address;
        this.password = password;
        this.repository = new AccountRepository(context);
    }

    /**
     * Allows user to login to account and calls methods to validate login credentials
     *
     * @return integer code representing success or failure of login
     */
    public boolean loginAccount() {
        boolean loginSuccess = true;
        int MAX_CONTACT_LENGTH = 20;
        if(this.address.length() < MAX_CONTACT_LENGTH) {
            loginSuccess = translateNameToAddress();
            if(loginSuccess == false) {
                return loginSuccess;
            }
        }

        loginSuccess = checkLoginCredentials(this.address, this.password);

        return loginSuccess;
    }

    /**
     * Validates user inputted login credentials
     * @param address the user inputted ethereum address or an alias wallet name
     * @param password the user inputted password corresponding to the wallet account
     * @return true or false depending on the validity of the credentials
     */
    private boolean checkLoginCredentials(String address, String password) {
        boolean proceed = true;
        if(!checkAddress() || !checkPassword()) {
            proceed = false;
        }
        return proceed;
    }

    /**
     * Validates a user inputted address
     * @return true or false depending on if the address exists in the database
     */
    private boolean checkAddress() {
        boolean proceed = false;
        String file = repository.getAccountFile(address);
        if(file != null) {
            proceed = true;
            fileName = file;
        }
        return proceed;
    }

    /**
     * Validates a user inputted password
     * @return true or false depending on if the password exists in the key store
     */
    private boolean checkPassword() {
        boolean proceed = true;

        PasswordModel passwordModel = new PasswordModel();
        String decryptedPassword = null;

        decryptedPassword = passwordModel.loadPassword(address,
                    Base64.decode(repository.getPassword(address), Base64.DEFAULT),
                    Base64.decode(repository.getInitVector(address), Base64.DEFAULT));

        if(!password.equals(decryptedPassword)) {
            proceed = false;
        }

        closeDatabase();

        return proceed;
    }

    /**
     * A helper method to close the database when it is no longer needed
     */
    private void closeDatabase() {
        repository.closeDatabase();
    }

    /**
     * A method to determine if the user input for wallet name is valid
     * @return true or false if the address corresponding to a wallet name exists
     */
    public boolean translateNameToAddress() {
        boolean exists = true;
        String address = repository.getContactAddress(this.address);
        if(address != null) {
            setAddress(address);
        }
        else {
            exists = false;
        }
        return exists;
    }

    /**
     * Sets the value of address
     * @param address an ethereum account address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for address instance data
     * @return the value of an account address
     */
    public String getAddress() {
        return this.address;
    }

    public String getFileName() {
        return this.fileName;
    }
}
