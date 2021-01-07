package com.example.capstonewallet;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WalletViewModel extends ViewModel {
    private MutableLiveData<String> password = new MutableLiveData<String>() {

    };

    private MutableLiveData<String> publicKey = new MutableLiveData<String>() {

    };

    private MutableLiveData<String> privateKey = new MutableLiveData<String>() {

    };

    private MutableLiveData<String> address = new MutableLiveData<String>() {

    };
}
