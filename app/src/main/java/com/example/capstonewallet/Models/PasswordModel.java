package com.example.capstonewallet.Models;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class PasswordModel {
    private static final String ENCRYPTION_SCHEME = "AES/GCM/NoPadding";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";

    private KeyStore keyStore;
    private String decryptedPassword;
    private String [] passwordRecord = new String[2];


    public String [] storePassword(String password, String alias) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException,
            NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException,
            InvalidAlgorithmParameterException, SignatureException, BadPaddingException,
            IllegalBlockSizeException {
        encryptPassword(password, alias);
        return passwordRecord;
    }

    // Login checks address by querying db, once verified, use address to query db for iv and encrypted password
    // then call method and compare result to user input to verify password and proceed with login
    public String loadPassword(String alias, byte[] encryptedPassword, byte[] initVector) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
        decryptPassword(alias, encryptedPassword, initVector);
        return this.decryptedPassword;
    }

    private void encryptPassword(String password, String alias) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        final Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
        //cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias));

        final KeyGenerator keyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);

        keyGenerator.init(new KeyGenParameterSpec.Builder(alias,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build());

        cipher.init(Cipher.ENCRYPT_MODE, keyGenerator.generateKey());
        byte [] initVector = cipher.getIV();

        byte [] encryption = cipher.doFinal(password.getBytes("UTF-8"));
        Log.d("yo123", encryption.toString());

        passwordRecord[0] = Base64.encodeToString(encryption, Base64.DEFAULT);
        passwordRecord[1] = Base64.encodeToString(initVector, Base64.DEFAULT);
        Log.d("yo123", Base64.encodeToString(encryption, Base64.DEFAULT));
        Log.d("yo123", Base64.encodeToString(initVector, Base64.DEFAULT));

    }
    private void decryptPassword(String alias, byte[] encryptedPassword, byte[] initVector) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, NoSuchPaddingException, UnrecoverableEntryException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);

        final Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
        final GCMParameterSpec parameters = new GCMParameterSpec(128, initVector);
        SecretKey secretKey = ((KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null)).getSecretKey();
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameters);
        decryptedPassword = new String(cipher.doFinal(encryptedPassword), "UTF-8");
    }
}
