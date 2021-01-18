package com.example.capstonewallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.LoginFragmentBinding;

public class TransactionFragment extends Fragment implements View.OnClickListener {
    FragmentTransactionBinding binding;
    TransactionModel transactionModel;
    /*
    public LoginFragment getInstance(Context context) {
        //super(R.layout.create_account);
        //walletModel = new WalletModel(context);
        return this;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());
        transactionModel = new TransactionModel();
        transactionModel.connectToEthNetwork();
        //transactionModel.sendEther("7889");
        final ImageButton sendEtherButton = (ImageButton) view.findViewById(binding.imageButton.getId());
        sendEtherButton.setOnClickListener(this::onClick);
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        //loginButton.setOnClickListener(this::onClick);
        //final Button createAccountButton = (Button) view.findViewById(fragmentBinding.createAccountButton.getId());
        //createAccountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "oncreateview");
        return view;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == binding.imageButton.getId()) {
            transactionModel.sendEther(binding.editTextTextPersonName4.getText().toString());
        }
    }
}