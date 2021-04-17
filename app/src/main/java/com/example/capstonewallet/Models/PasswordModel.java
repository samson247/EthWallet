package com.example.capstonewallet.Models;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * Model class for password encryption and decryption for wallet accounts
 */
public class PasswordModel {
    private static final String ENCRYPTION_SCHEME = "AES/GCM/NoPadding";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private KeyStore keyStore;
    private Cipher cipher;
    private String decryptedPassword;
    private KeyGenerator keyGenerator;
    private String [] passwordRecord = new String[3];


    /**
     * Password is encrypted and the result is returned to the user
     * @param password the password to encrypt
     * @param alias the string to encrypt the password with
     * @return the encrypted password and initialization vector
     */
    public String [] storePassword(String password, String alias) {
        try {
            encryptPassword(password, alias);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return passwordRecord;
    }

    /**
     * Password is decrypted and returned to user, used when verifying password at login is correct
     * @param alias the string used to encrypt the password
     * @param encryptedPassword the encrypted password
     * @param initVector the initialization vector for the password
     * @return the decrypted password
     */
    public String loadPassword(String alias, byte[] encryptedPassword, byte[] initVector) {
        try {
            decryptPassword(alias, encryptedPassword, initVector);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return this.decryptedPassword;
    }

    /**
     * Encrypts the password using the specified encryption scheme
     * @param password the password for a wallet account to encrypt
     * @param alias the string being used to encrypt the password
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private void encryptPassword(String password, String alias) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
        keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);
        keyGenerator.init(new KeyGenParameterSpec.Builder(alias,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build());
        cipher.init(Cipher.ENCRYPT_MODE, keyGenerator.generateKey());
        byte [] initVector = cipher.getIV();
        byte [] encryption = cipher.doFinal(password.getBytes("UTF-8"));
        passwordRecord[0] = Base64.encodeToString(encryption, Base64.DEFAULT);
        passwordRecord[1] = Base64.encodeToString(initVector, Base64.DEFAULT);
    }

    /**
     * Decrypts an encrypted wallet password
     * @param alias the alias for the encrypted password in the Android key store
     * @param encryptedPassword the encrypted password
     * @param initVector the initialization vector for the password
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws UnrecoverableEntryException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private void decryptPassword(String alias, byte[] encryptedPassword, byte[] initVector)
            throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException,
            NoSuchPaddingException, UnrecoverableEntryException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);

        cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
        final GCMParameterSpec parameters = new GCMParameterSpec(128, initVector);
        SecretKey secretKey = ((KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null)).getSecretKey();
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameters);
        decryptedPassword = new String(cipher.doFinal(encryptedPassword), "UTF-8");
    }
}
