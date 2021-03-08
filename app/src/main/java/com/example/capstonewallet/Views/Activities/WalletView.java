package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.capstonewallet.Views.Fragments.AccountFragment;
import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Views.Fragments.AddressBookFragment;
import com.example.capstonewallet.Views.Fragments.BottomNavigationFragment;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.StockNewsFragment;
import com.example.capstonewallet.Views.Fragments.TestFragment;
import com.example.capstonewallet.Views.Fragments.TransactionFragment;
import com.example.capstonewallet.Views.Fragments.TransactionListFragment;
import com.example.capstonewallet.viewmodels.WalletViewModel;
import com.example.capstonewallet.databinding.WalletBinding;

import java.math.BigDecimal;

public class WalletView extends AppCompatActivity {
    private WalletViewModel walletViewModel;
    private WalletBinding walletBinding;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationFragment bottomNavigationFragment;
    TransactionFragment transactionFragment;
    BraintreeClient client;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        // initialize walletviewmodel with intent
        String [] credentials = getIntent().getExtras().getStringArray("credentials");
        if(credentials.length == 2) {
            String password = credentials[0];
            String fileName = credentials[1];
            Log.d("yo123", "walletview" + password + " " + fileName);
            walletViewModel = new WalletViewModel(getApplicationContext(), password, fileName);
        }
        else if(credentials.length == 1) {
            String privateKey = credentials[0];
            walletViewModel = new WalletViewModel(getApplicationContext(), privateKey);
        }

        walletBinding = WalletBinding.inflate(getLayoutInflater());

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // Bottom navigation fragment is displayed to user
        bottomNavigationFragment = new BottomNavigationFragment();
        bottomNavigationFragment.setWalletView(this);
        bottomNavigationFragment.setFragmentManager(fragmentManager);
        fragmentTransaction.add(walletBinding.containerBottom.getId(), bottomNavigationFragment, null);

        // Transaction fragment is displayed to user
        transactionFragment = new TransactionFragment(this);
        Bundle bundle = new Bundle();
        bundle.putString("privateKey", walletViewModel.getPrivateKey());
        transactionFragment.setArguments(bundle);
        transactionFragment.setWalletView(this);
        fragmentTransaction.add(walletBinding.containerTop.getId(), transactionFragment, "transaction");
        fragmentTransaction.addToBackStack("transaction").commit();
        fragmentManager.executePendingTransactions();
    }

    public void switchTopFragment(String fragmentStr) {
        Fragment fragment;
        if(fragmentStr.equals("TransactionFragment")) {
            fragment = new TransactionFragment(this);
            Bundle bundle = new Bundle();
            bundle.putString("privateKey", walletViewModel.getPrivateKey());
            fragment.setArguments(bundle);
            //((TransactionFragment) fragment).setWalletView(this);
        }
        else if(fragmentStr.equals("StockNewsFragment")) {
            fragment = new StockNewsFragment();
            Bundle bundle = new Bundle();
            //bundle.putStringArrayList();
        }
        else if(fragmentStr.equals("AccountFragment")) {
            fragment = new AccountFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("credentials", walletViewModel.getCredentials());
            fragment.setArguments(bundle);
        }
        else if(fragmentStr.equals("AddressBookFragment")) {
            fragment = new AddressBookFragment();
            //((AddressBookFragment) fragment).setWalletView(this);
        }
        else {
            fragment = new Fragment();
        }

        if(fragmentManager.findFragmentById(walletBinding.containerTop.getId()) != null) {
            fragmentManager.beginTransaction().remove(transactionFragment).commit();
        }
        //fragman.beginTransaction().remove(fragman.findFragmentById(R.id.fraglog)).commit();
        fragmentManager.beginTransaction().replace(walletBinding.containerTop.getId(), fragment).commit();
    }


    public int startBraintree()  {
        String token = walletViewModel.getToken();
        DropInRequest dropInRequest;
        if(token != null) {
            dropInRequest = new DropInRequest().clientToken(token);
            Log.d("yo123", "newway");
        }
        else {
            try {
                client = new BraintreeClient();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dropInRequest = new DropInRequest().clientToken(client.getClientToken());
            Log.d("yo123", "oldway");
        }
        try {
            client = new BraintreeClient();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dropInRequest = new DropInRequest().clientToken(client.getClientToken());
        Log.d("yo123", "dropin created");
        startActivityForResult(dropInRequest.getIntent(this), 400);

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.result = resultCode;
        Log.d("onactres", "here walletview");
        Log.d("onactres", "request " + requestCode);
        Log.d("onactres", "result " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onactres", "request " + requestCode);
        Log.d("onactres", "result " + resultCode);
        Log.d("onactres", "here walletview post super");
        String amount = transactionFragment.getEtherAmount();
        client.onActivityResult(requestCode, resultCode, data, amount);
        //FIXME find a way to move client out of activity
        //transactionFragment.onActivityResult(requestCode, resultCode, data, amount);
    }

    public WalletViewModel getWalletViewModel() {
        return this.walletViewModel;
    }
}