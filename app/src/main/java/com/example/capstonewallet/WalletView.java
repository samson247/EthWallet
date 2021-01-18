package com.example.capstonewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.capstonewallet.databinding.FragmentBottomNavigationBinding;
import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.WalletBinding;

public class WalletView extends AppCompatActivity {
    private WalletViewModel walletViewModel;
    private WalletBinding walletBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        // initialize walletviewmodel with intent
        walletBinding = WalletBinding.inflate(getLayoutInflater());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BottomNavigationFragment bottomNavigationFragment = new BottomNavigationFragment();
        bottomNavigationFragment.setFragmentManager(fragmentManager);
        //bottomNavigationFragment.setFragmentTransaction(fragmentTransaction);
        fragmentTransaction.add(walletBinding.containerBottom.getId(), bottomNavigationFragment, "");
        //fragmentTransaction.commit();

        TransactionFragment transactionFragment = new TransactionFragment();
        fragmentTransaction.add(walletBinding.containerTop.getId(), transactionFragment, "");
        fragmentTransaction.commit();


    }
}