package com.example.capstonewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.capstonewallet.databinding.FragmentBottomNavigationBinding;
import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.WalletBinding;

public class WalletView extends AppCompatActivity {
    private WalletViewModel walletViewModel;
    private WalletBinding walletBinding;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationFragment bottomNavigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        // initialize walletviewmodel with intent
        walletBinding = WalletBinding.inflate(getLayoutInflater());

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        bottomNavigationFragment = new BottomNavigationFragment();
        bottomNavigationFragment.setFragmentManager(fragmentManager);
        //bottomNavigationFragment.setFragmentTransaction(fragmentTransaction);
        fragmentTransaction.add(walletBinding.containerBottom.getId(), bottomNavigationFragment, "");
        //fragmentTransaction.commit();

        TransactionFragment transactionFragment = new TransactionFragment();
        transactionFragment.setWalletView(this);
        //transactionFragment.setFragmentManager(fragmentManager);
        //transactionFragment.setFragmentTransaction(fragmentTransaction);
        fragmentTransaction.add(walletBinding.containerTop.getId(), transactionFragment, "");
        fragmentTransaction.commit();
    }

    public void addListFragment() {
        //fragmentTransaction = fragmentManager.beginTransaction();
        TransactionListFragment listFragment = new TransactionListFragment();
        //fragmentTransaction.add(walletBinding.containerMiddle.getId(), listFragment, "");
        //fragmentTransaction.commit();

        fragmentManager.beginTransaction().add(walletBinding.containerMiddle.getId(), listFragment, "").commitNow();
        fragmentManager.beginTransaction().hide(bottomNavigationFragment).commitNow();

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
}