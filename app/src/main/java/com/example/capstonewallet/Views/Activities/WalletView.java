package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.braintreepayments.api.dropin.DropInRequest;
import com.example.capstonewallet.Views.Fragments.AccountFragment;
import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Views.Fragments.AddressBookFragment;
import com.example.capstonewallet.Views.Fragments.BottomNavigationFragment;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.StockNewsFragment;
import com.example.capstonewallet.Views.Fragments.TransactionFragment;
import com.example.capstonewallet.Views.Fragments.TransactionListFragment;
import com.example.capstonewallet.viewmodels.WalletViewModel;
import com.example.capstonewallet.databinding.WalletBinding;

public class WalletView extends AppCompatActivity {
    private WalletViewModel walletViewModel;
    private WalletBinding walletBinding;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationFragment bottomNavigationFragment;
    TransactionFragment transactionFragment;
    BraintreeClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        // initialize walletviewmodel with intent
        String password = getIntent().getExtras().getString("password");
        String fileName = getIntent().getExtras().getString("fileName");

        Log.d("yo123", "walletview" + password + " " + fileName);
        walletViewModel = new WalletViewModel(getApplicationContext(), password, fileName);

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
        //transactionFragment.setWalletView(this);
        fragmentTransaction.add(walletBinding.containerTop.getId(), transactionFragment, null);
        fragmentTransaction.commit();

    }

    /*public void addListFragment() {
        TransactionListFragment listFragment = new TransactionListFragment();

        fragmentManager.beginTransaction().add(walletBinding.containerMiddle.getId(), listFragment, "").commitNow();
        fragmentManager.beginTransaction().hide(bottomNavigationFragment).commitNow();
        Log.d("yo123", "Added list fragment");

    }*/

    /*public void addAddContactFragment() {
        AddContactFragment fragment = new AddContactFragment(getApplicationContext());
        fragmentManager.beginTransaction().add(walletBinding.containerMiddle.getId(), fragment, "").commitNow();
    }*/


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


    /*public void startBraintree()  {
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
        DropInRequest dropInRequest = new DropInRequest().clientToken(client.getClientToken());
        Log.d("yo123", "dropin created");
        startActivityForResult(dropInRequest.getIntent(this), 400);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String amount = transactionFragment.getEtherAmount();
        client.onActivityResult(requestCode, resultCode, data, amount);
    }

    public WalletViewModel getWalletViewModel() {
        return this.walletViewModel;
    }
}