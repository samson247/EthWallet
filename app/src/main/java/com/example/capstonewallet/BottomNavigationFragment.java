package com.example.capstonewallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstonewallet.databinding.FragmentBottomNavigationBinding;
import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.LoginFragmentBinding;



public class BottomNavigationFragment extends Fragment implements View.OnClickListener {
    private FragmentBottomNavigationBinding binding;
    private FragmentManager fragmentManager;
    private WalletView walletView;

    /*
    public LoginFragment getInstance(Context context) {
        //super(R.layout.create_account);
        //walletModel = new WalletModel(context);
        return this;
    }*/

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setWalletView(WalletView walletView) {
        this.walletView = walletView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
        binding = FragmentBottomNavigationBinding.inflate(getLayoutInflater());
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        //loginButton.setOnClickListener(this::onClick);
        final Button transactionButton = (Button) view.findViewById(binding.button2.getId());
        transactionButton.setOnClickListener(this::onClick);
        final Button stockNewsButton = (Button) view.findViewById(binding.button3.getId());
        stockNewsButton.setOnClickListener(this::onClick);
        final Button accountButton = (Button) view.findViewById(binding.button4.getId());
        accountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "oncreateview Bottom Frag");
        return view;
    }


    @Override
    public void onClick(View v) {
        Log.d("yo123", "inonclick");
        String fragmentClass = "";
        if(v.getId() == binding.button2.getId()) {
            //TransactionFragment fragment = new TransactionFragment();
            fragmentClass = "TransactionFragment";
            //walletView.switchTopFragment("TransactionFragment");
        }
        else if(v.getId() == binding.button3.getId()) {
            fragmentClass = "StockNewsFragment";
            //walletView.switchTopFragment("");
        }
        else if(v.getId() == binding.button4.getId()) {
            //AccountFragment fragment = new AccountFragment();
            fragmentClass = "AccountFragment";
            //walletView.switchTopFragment("AccountFragment");
        }

        walletView.switchTopFragment(fragmentClass);

    }
}