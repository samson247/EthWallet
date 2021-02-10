package com.example.capstonewallet.ViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonewallet.Models.WalletModel;

public class WalletViewModel extends ViewModel {
    private WalletModel walletModel;

    public WalletViewModel(Context context) {
        walletModel = new WalletModel(context);
        walletModel.loadWalletFromFile("q", "t", false);
        //Do something similar to this to set values: setPublicKey(walletModel.getPublicKey());
    }

    private MutableLiveData<String> password = new MutableLiveData<String>() {

    };

    private MutableLiveData<String> publicKey = new MutableLiveData<String>() {

    };

    private MutableLiveData<String> privateKey = new MutableLiveData<String>() {

    };

    private MutableLiveData<String> address = new MutableLiveData<String>() {

    };
}
