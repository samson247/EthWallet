package com.example.capstonewallet.Views.Fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstonewallet.AccountRepository;
import com.example.capstonewallet.CreateAccountFragment;
import com.example.capstonewallet.ViewModels.LoginViewModel;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.LoginFragmentBinding;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private LoginViewModel loginViewModel;
    private LoginFragmentBinding fragmentBinding;
    private LoginBinding loginBinding;
    private AccountRepository repository;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    EditText password;
    EditText address;

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

        //repository = new AccountRepository(getContext());
        final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        loginButton.setOnClickListener(this::onClick);
        //final Button createAccountButton = (Button) view.findViewById(fragmentBinding.createAccountButton.getId());
        //createAccountButton.setOnClickListener(this::onClick);
        final TextView createAccountTextView = (TextView) view.findViewById(fragmentBinding.createOrAddTextView.getId());
        createAccountTextView.setOnClickListener(this::onClick);

        password = (EditText) view.findViewById(R.id.editTextTextPersonName2);
        address = (EditText) view.findViewById(R.id.editTextTextPersonName);

        Log.d("yo123", "oncreateview");
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d("yo123", "inonclick");
        if(v.getId() == fragmentBinding.loginButton.getId()) {
            //String address = "";
            if(fragmentBinding.editTextTextPersonName.getText().toString().length() < 20) {
                //address = repository.getContactName(fragmentBinding.editTextTextPersonName.getText().toString());
                //checkLoginCredentials(address, fragmentBinding.editTextTextPersonName2.getText().toString());
            }
            else {
                //checkLoginCredentials(fragmentBinding.editTextTextPersonName.getText().toString(), fragmentBinding.editTextTextPersonName2.getText().toString());
            }
            //checkLoginCredentials(address, fragmentBinding.editTextTextPersonName2.getText().toString());
            //Put input validation in model call from setters below in ViewModel
            loginViewModel.setPublicKey(address.getText().toString());
            loginViewModel.setPassword(password.getText().toString());
            boolean proceed = loginViewModel.onClick(getContext());

            if(proceed) {
                Intent intent = new Intent(getActivity(), WalletView.class);
                // pass credentials
                startActivity(intent);
            }
            else {
                address.setHintTextColor(Color.RED);
                password.setHintTextColor(Color.RED);
            }
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
        if(!checkAddress(address) || !checkPassword(password)) {
            proceed = false;
        }
        return proceed;
    }

    private boolean checkAddress(String address) {
        boolean proceed = false;
        Log.d("yo123", "chk: " + address);
        //boolean proceed = repository.checkAddress("0x2a101ff0d72e2624530bc542c64ca80b902ed55d");
        String temp = repository.getAccountFile(address);
        Log.d("yo123", "chk: " + temp);
        if(temp != null) {
            proceed = true;
        }
        return proceed;
    }
    private boolean checkPassword(String password) {
        boolean proceed = true;

        return proceed;
    }

    /*private String getAddress() {
        return repository.getAddress(username);
    }*/
}