package com.example.capstonewallet;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.LoginFragmentBinding;
import com.example.capstonewallet.databinding.WalletBinding;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private LoginViewModel loginViewModel;
    private LoginFragmentBinding fragmentBinding;
    private LoginBinding loginBinding;
    private AccountRepository repository;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setFragmentTransaction(FragmentTransaction fragmentTransaction) {
        this.fragmentTransaction = fragmentTransaction;
    }
    public LoginFragment getInstance(Context context) {
        //super(R.layout.create_account);
        //walletModel = new WalletModel(context);
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        fragmentBinding = LoginFragmentBinding.inflate(getLayoutInflater());
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        loginButton.setOnClickListener(this::onClick);
        //final Button createAccountButton = (Button) view.findViewById(fragmentBinding.createAccountButton.getId());
        //createAccountButton.setOnClickListener(this::onClick);
        final TextView createAccountTextView = (TextView) view.findViewById(fragmentBinding.createOrAddTextView.getId());
        createAccountTextView.setOnClickListener(this::onClick);

        Log.d("yo123", "oncreateview");
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d("yo123", "inonclick");
        if(v.getId() == fragmentBinding.loginButton.getId()) {
            if(fragmentBinding.editTextTextPersonName.getText().toString().length() < 20) {
                getAddress();
            }

            checkLoginCredentials(fragmentBinding.editTextTextPersonName.getText().toString(), fragmentBinding.editTextTextPersonName2.getText().toString());
            loginViewModel.setPublicKey(fragmentBinding.editTextTextPersonName.getText().toString());
            loginViewModel.setPassword(fragmentBinding.editTextTextPersonName2.getText().toString());
            loginViewModel.onClick(getContext());
            Intent intent = new Intent(getActivity(), WalletView.class);
            // pass credentials
            startActivity(intent);
        }
        else if (v.getId() == fragmentBinding.createOrAddTextView.getId()){
            Log.d("yo123", "onclick");
            if(fragmentManager.findFragmentById(R.id.login_fragment) != null) {
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.login_fragment)).commit();
            }
            //fragman.beginTransaction().remove(fragman.findFragmentById(R.id.fraglog)).commit();
            CreateAccountFragment fragment = new CreateAccountFragment();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            Log.d("yo123", "onclicked");
            //fragtran.add(R.id.trans, fm3, "HELLO");
            //fragtran.commit();
        }
    }

    private boolean checkLoginCredentials(String address, String password) {
        boolean proceed = true;
        if(!checkPublicKey() || !checkPassword()) {
            proceed = false;
        }
        return proceed;
    }

    private boolean checkPublicKey() {
        boolean proceed = false;
        String [] keys = repository.getKeys();

        for(key in keys) {
            if key = publicKey {
                proceed = true;
            }
        }
        return proceed;

    }
    private boolean checkPassword() {
        boolean proceed = false;

        String[] passwords = repository.getPasswords();

        for (password in passwords) {
            if password = this.password {
                proceed = true;
            }
        }

        return proceed;
    }

    private String getAddress() {
        return repository.getAddress(username);
    }
}