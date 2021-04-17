package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.braintreepayments.api.dropin.DropInRequest;
import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.Views.Fragments.AccountFragment;
import com.example.capstonewallet.Views.Fragments.AddressBookFragment;
import com.example.capstonewallet.Views.Fragments.BottomNavigationFragment;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.NewsFragment;
import com.example.capstonewallet.Views.Fragments.TransactionFragment;
import com.example.capstonewallet.viewmodels.WalletViewModel;
import com.example.capstonewallet.databinding.WalletBinding;

/**
 * View class for main activity for wallet, in charge of presenting different fragments to user
 */
public class WalletView extends AppCompatActivity {
    private WalletViewModel walletViewModel;
    private WalletBinding walletBinding;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationFragment bottomNavigationFragment;
    private TransactionFragment transactionFragment;
    private AddressBookFragment addressBookFragment;
    private NewsFragment newsFragment;
    private AccountFragment accountFragment;
    private FragmentContainerView containerTop;
    private BraintreeClient client;
    private ProgressBar loadingScreen;
    private int result = -100;

    /**
     * Creates view of wallet class and loads transaction fragment
     * @param savedInstanceState the previously saved instance state of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        // initialize walletviewmodel with intent
        String [] credentials = getIntent().getExtras().getStringArray("credentials");
        if(credentials.length == 2) {
            String password = credentials[0];
            String fileName = credentials[1];
            walletViewModel = new WalletViewModel(getApplicationContext(), password, fileName);
        }
        else if(credentials.length == 1) {
            String privateKey = credentials[0];
            walletViewModel = new WalletViewModel(getApplicationContext(), privateKey);
        }

        loadingScreen = findViewById(R.id.loadingScreen);
        containerTop = findViewById(R.id.container_top);

        walletBinding = WalletBinding.inflate(getLayoutInflater());
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // Bottom navigation fragment is displayed to user
        bottomNavigationFragment = new BottomNavigationFragment();
        bottomNavigationFragment.setWalletView(this);
        bottomNavigationFragment.setFragmentManager(fragmentManager);
        fragmentTransaction.add(walletBinding.containerBottom.getId(), bottomNavigationFragment, null);

        // Transaction fragment is displayed to user
        getSupportActionBar().setTitle("Transactions");
        transactionFragment = new TransactionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("privateKey", walletViewModel.getPrivateKey());
        transactionFragment.setArguments(bundle);
        fragmentTransaction.add(walletBinding.containerTop.getId(), transactionFragment, "TransactionFragment");
        fragmentTransaction.addToBackStack("transaction").commit();
        fragmentManager.executePendingTransactions();
    }

    /**
     * Handles the switching of the top fragment based on what is selected in bottom nav fragment
     * @param fragmentStr the name of the fragment to transition to
     */
    public void switchTopFragment(String fragmentStr) {
        Fragment fragment;
        if(fragmentStr.equals("TransactionFragment")) {
            if(transactionFragment == null) {
                fragment = new TransactionFragment();
                transactionFragment = (TransactionFragment) fragment;
                Log.d("yo123", "new transfrag");
            }
            else {
                fragment = transactionFragment;
            }
            Bundle bundle = new Bundle();
            bundle.putString("privateKey", walletViewModel.getPrivateKey());
            fragment.setArguments(bundle);
            getSupportActionBar().setTitle("Transactions");
        }
        else if(fragmentStr.equals("StockNewsFragment")) {
            //walletBinding.containerTop.setVisibility(View.INVISIBLE);
            //loadingScreen.setVisibility(View.VISIBLE);
            fragment = new NewsFragment();
            newsFragment = (NewsFragment) fragment;
            getSupportActionBar().setTitle("Ethereum News");
        }
        else if(fragmentStr.equals("AccountFragment")) {
            fragment = new AccountFragment();
            accountFragment = (AccountFragment) fragment;
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("credentials", walletViewModel.getCredentials());
            fragment.setArguments(bundle);
            getSupportActionBar().setTitle("Account");
        }
        else if(fragmentStr.equals("AddressBookFragment")) {
            fragment = new AddressBookFragment();
            addressBookFragment = (AddressBookFragment) fragment;
            getSupportActionBar().setTitle("Address Book");
        }
        else {
            fragment = new Fragment();
        }

        /*if(fragmentManager.findFragmentById(walletBinding.containerTop.getId()) != null) {
            fragmentManager.beginTransaction().remove(transactionFragment).commit();
        }*/

        fragmentManager.beginTransaction().replace(walletBinding.containerTop.getId(),
                    fragment, fragmentStr).commit();

        walletBinding.containerTop.setVisibility(View.VISIBLE);
    }

    /**
     * TODO fix this
     */
    public void startBraintree()  {
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
    }

    /**
     * Called when Braintree activity has completed, notifies transaction fragment of result
     * @param requestCode the request code from Braintree
     * @param resultCode the result of the Braintree activity
     * @param data the data bundle containing Braintree's info
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.result = resultCode;
        super.onActivityResult(requestCode, resultCode, data);
        String amount = transactionFragment.getEtherAmount();
        int result = client.onActivityResult(requestCode, resultCode, data, amount);
        notifyGetFragment(result);
    }

    /**
     * Getter for result of Braintree
     * @return the result of Braintree
     */
    public int getResult() {
        return this.result;
    }

    /**
     * Notifies transaction get fragment of Braintree result
     * @param resultCode the result of Braintree
     */
    public void notifyGetFragment(int resultCode) {
        transactionFragment.notifyGet(resultCode);
    }

    /**
     * Getter for wallet view model instance
     * @return the wallet view model instance of this class
     */
    public WalletViewModel getWalletViewModel() {
        return this.walletViewModel;
    }

    /**
     * Hides the bottom navigation fragment
     */
    public void hideNavBar() {
        getSupportFragmentManager().beginTransaction().hide(bottomNavigationFragment).commit();
    }

    /**
     * Shows the bottom navigation fragment
     */
    public void showNavBar() {
        getSupportFragmentManager().beginTransaction().show(bottomNavigationFragment).commit();
    }

    /**
     * Controls the removal of fragments when home back arrow is pressed
     * @param item the activity that generated the click event
     * @return true in all cases
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(addressBookFragment != null && addressBookFragment.isVisible()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            addressBookFragment.popAddContactFragment();
        }
        else if(transactionFragment != null && transactionFragment.isVisible()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            transactionFragment.popTransactionInfoFragment();
        }
        else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            accountFragment.popSettingsFragment();
        }
        return true;
    }

    /**
     * Hides top fragment
     */
    public void hideTopFragment() {
        containerTop.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows top fragment
     */
    public void showTopFragment() {
        containerTop.setVisibility(View.VISIBLE);
    }

    /**
     * Shows loading screen
     */
    public void showLoadingScreen() {
        loadingScreen.setVisibility(View.VISIBLE);
    }

    /**
     * Hides loading screen
     */
    public void hideLoadingScreen() {
        loadingScreen.setVisibility(View.INVISIBLE);
    }
}