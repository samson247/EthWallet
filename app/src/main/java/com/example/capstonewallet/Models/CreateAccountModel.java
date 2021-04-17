package com.example.capstonewallet.Models;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.capstonewallet.AccountRepository;
import com.example.capstonewallet.Database.ContactEntity;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Model class for the create account fragment
 *
 * @author Sam Dodson
 */
public class CreateAccountModel {
    private AccountRepository repository;
    private String address;
    private String name;
    private String fileName;
    private String password;
    String privateKey;
    private static final String DIRECTORY_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS;
    int code;

    /**
     * Constructor for the CreateAccountModel class
     * @param context the context for the CreateAccount fragment, used to initialize DB repository
     * @param name the name of the wallet
     */
    public CreateAccountModel(Context context, String name) {
        repository = new AccountRepository(context);
        this.name = name;
    }

    /**
     * Creates a new wallet file using password
     * @param password the password used to encrypt wallet file
     * @return true or false depending on if the wallet was created successfully
     */
    public int createWallet(String password, String name)
    {
        // Wallet is created if password is of valid format otherwise return false
        code = -1;
        if (!checkUsername(name)) {
            if (checkPassword(password)) {
                this.setupSecurityProvider();
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

                // If file is created successfully account, name, and password is added to database
                if (fileName != null) {
                    setPassword(password);
                    setFileName(path + "/" + fileName);
                    insertAccount();
                    Log.d("yo123", "account inserted");
                    insertName();
                    Log.d("yo123", "name inserted");
                    insertPassword();
                    Log.d("yo123", "password inserted");
                }
            }
        }
        return code;
    }

    /**
     * Account is inserted into database Account table
     */
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

    /**
     * Existing account is added to database
     */
    public void addExistingAccount() {
        Credentials credentials = null;
        credentials = Credentials.create(privateKey);
        setAddress(credentials.getAddress());
        insertName();
        insertPassword();
    }

    /**
     * Wallet name is inserted into Contact table
     */
    public void insertName() {
        repository.addContact(address, name);
    }

    /**
     * Password is added to Android Keystore and encrypted password is added to Password table
     */
    public void insertPassword() {
        PasswordModel passwordModel = new PasswordModel();
        String [] passwordRecord = new String[3];

        passwordRecord = passwordModel.storePassword(password, address);

        repository.insertPassword(address, passwordRecord[0], passwordRecord[1]);
    }

    /**
     * Getter for the address of the newly created wallet account
     * @return the address of the wallet account
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for the address value
     * @param address the address of the wallet account
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for the name of the wallet account
     * @return the name of the wallet account
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the wallet account
     * @param name the name of the wallet account
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the file name associated with wallet account
     * @return the filename of the wallet file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter for the file name associated with wallet account
     * @param fileName the name of the wallet file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter for the password associated with wallet account
     * @return the password for a wallet account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password associated with wallet account
     * @param password the password for a wallet account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets up Java security provider with Bouncy Castle provider if not already set
     */
    public void setupSecurityProvider() {
        final Provider securityProvider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (securityProvider == null) {
            return;
        }
        if (securityProvider.getClass().equals(BouncyCastleProvider.class)) {
            return;
        }
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    /**
     * Verifies password is in a valid format
     * @param password the user entered password to verify
     * @return true is password is valid and false otherwise
     */
    public boolean checkPassword(String password) {
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,}$");
        Matcher m = p.matcher(password);
        boolean isMatch = m.matches();
        if(isMatch == false) {
            code = 1;
        }

        return isMatch;
    }

    /**
     * Checks that wallet name is unique and less than 20 characters
     * @param name the name to check
     * @return the code specifying whether the name is valid or not
     */
    public boolean checkUsername(String name) {
        if(name.length() > 20) {
            code = 2;
            return true;
        }
        ContactEntity[] contacts = repository.getContacts();
        boolean exists = false;
        for(int index = 0; index < contacts.length; index++) {
            if(!(contacts[index].getName() == null)) {
                if(contacts[index].getName().equals(name)) {
                    Log.d("yo123", "exists");
                    exists = true;
                }
            }
        }
        if(exists == true) {
            code = 0;
        }
        return exists;
    }
}
