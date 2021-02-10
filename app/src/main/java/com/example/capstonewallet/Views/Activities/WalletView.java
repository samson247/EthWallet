package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.braintreepayments.api.dropin.DropInRequest;
import com.example.capstonewallet.AccountFragment;
import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Views.Fragments.AddressBookFragment;
import com.example.capstonewallet.Views.Fragments.BottomNavigationFragment;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.StockNewsFragment;
import com.example.capstonewallet.Views.Fragments.TransactionFragment;
import com.example.capstonewallet.Views.Fragments.TransactionListFragment;
import com.example.capstonewallet.ViewModels.WalletViewModel;
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
        walletBinding = WalletBinding.inflate(getLayoutInflater());

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        bottomNavigationFragment = new BottomNavigationFragment();
        bottomNavigationFragment.setWalletView(this);
        bottomNavigationFragment.setFragmentManager(fragmentManager);
        //bottomNavigationFragment.setFragmentTransaction(fragmentTransaction);
        fragmentTransaction.add(walletBinding.containerBottom.getId(), bottomNavigationFragment, "");

        //fragmentTransaction.commit();
        //fragmentManager.beginTransaction().add(walletBinding.containerBottom.getId(), bottomNavigationFragment, "").commitNow();

        transactionFragment = new TransactionFragment();
        transactionFragment.setWalletView(this);
        //transactionFragment.setFragmentManager(fragmentManager);
        //transactionFragment.setFragmentTransaction(fragmentTransaction);
        fragmentTransaction.add(walletBinding.containerTop.getId(), transactionFragment, "");
        fragmentTransaction.commit();
        /*new Thread(() -> {
            try {

                // code runs in a thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragmentManager.beginTransaction().add(walletBinding.containerTop.getId(), transactionFragment, "").commitNow();
                    }
                });
            } catch (final Exception ex) {
                Log.i("---","Exception in thread");
            }
        }).start();*/
    }

    public void addListFragment() {
        //fragmentTransaction = fragmentManager.beginTransaction();
        TransactionListFragment listFragment = new TransactionListFragment();
        //fragmentTransaction.add(walletBinding.containerMiddle.getId(), listFragment, "");
        //fragmentTransaction.commit();

        fragmentManager.beginTransaction().add(walletBinding.containerMiddle.getId(), listFragment, "").commitNow();
        fragmentManager.beginTransaction().hide(bottomNavigationFragment).commitNow();
        Log.d("yo123", "Added list fragment");

        /*
        if(fragmentManager.findFragmentById(R.id.bottom_navigation) != null) {
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.bottom_navigation)).commit();
        }
        //fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.fraglog)).commit();
        //CreateAccountFragment fragment = new CreateAccountFragment();
        TransactionListFragment listFragment = new TransactionListFragment();

        fragmentManager.beginTransaction().replace(R.id.container_bottom, listFragment).commit();
        LinearLayout layout = walletBinding.containerBottom;
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        //params.height = 400;*/

    }

    public void addAddContactFragment() {
        AddContactFragment fragment = new AddContactFragment(getApplicationContext());
        fragmentManager.beginTransaction().add(walletBinding.containerMiddle.getId(), fragment, "").commitNow();
    }

    /*public void switchTopFragment(Fragment fragment) {
        if(fragmentManager.findFragmentById(walletBinding.containerTop.getId()) != null) {
            fragmentManager.beginTransaction().remove(transactionFragment).commit();
        }
        //fragman.beginTransaction().remove(fragman.findFragmentById(R.id.fraglog)).commit();
        fragmentManager.beginTransaction().replace(walletBinding.containerTop.getId(), fragment).commit();

        if(fragment.getClass().getName() == "TransactionFragment") {
            fragmentTransaction = (FragmentTransaction) fragment;

        }
    }*/

    public void switchTopFragment(String fragmentStr) {
        Fragment fragment;
        if(fragmentStr == "TransactionFragment") {
            fragment = new TransactionFragment();
            ((TransactionFragment) fragment).setWalletView(this);
        }
        else if(fragmentStr == "StockNewsFragment") {
            fragment = new StockNewsFragment();
        }
        else if(fragmentStr == "AccountFragment") {
            fragment = new AccountFragment();
            ((AccountFragment) fragment).setWalletView(this);
            ((AccountFragment) fragment).setCredentials(new String[]{"add", "app"});
        }
        else if(fragmentStr == "AddressBookFragment") {
            fragment = new AddressBookFragment();
            ((AddressBookFragment) fragment).setWalletView(this);
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


    public void startBraintree()  {
        try {
            client = new BraintreeClient();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DropInRequest dropInRequest = new DropInRequest().clientToken(client.getClientToken());
        Log.d("yo123", "dropin created");
        startActivityForResult(dropInRequest.getIntent(this), 400);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String amount = transactionFragment.getEtherAmount();
        client.onActivityResult(requestCode, resultCode, data, amount);
    }
}