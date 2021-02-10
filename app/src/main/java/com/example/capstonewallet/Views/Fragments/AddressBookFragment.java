package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddressBookFragment extends Fragment implements View.OnClickListener {
    //recyclerview
    //button to add new contact
    //Alphabetically organized
    //Search feature
    private FloatingActionButton createAccountButton;
    private WalletView walletView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.address_book_fragment, container, false);


        createAccountButton = (FloatingActionButton) view.findViewById(R.id.addContactButton);
        createAccountButton.setOnClickListener(this::onClick);
        //fragmentManager = getSupportFragmentManager();
        //fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        //loginButton.setOnClickListener(this::onClick);
        //final Button createAccountButton = (Button) view.findViewById(fragmentBinding.createAccountButton.getId());
        //createAccountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "oncreateview Address book Frag");
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == createAccountButton.getId()) {
            walletView.addAddContactFragment();
        }
    }

    public void setWalletView(WalletView walletView) {
        this.walletView = walletView;
    }
}
