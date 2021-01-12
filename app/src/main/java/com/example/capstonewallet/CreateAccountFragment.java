package com.example.capstonewallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstonewallet.databinding.WalletBinding;


public class CreateAccountFragment extends Fragment implements View.OnClickListener {
    private WalletModel walletModel;
    private com.example.capstonewallet.databinding.LoginBinding binding;

    public CreateAccountFragment getInstance(Context context) {
        //super(R.layout.create_account);
        walletModel = new WalletModel(context);
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.create_account, container, false);
        binding = com.example.capstonewallet.databinding.LoginBinding.inflate(getLayoutInflater());
        final Button createAccountButton = (Button) view.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onClick(View v) {
        getInstance(getContext());
        walletModel.createWallet(binding.textView2.getText().toString());
        //popup showing credentials and telling them to take note
        Intent intent = new Intent(getActivity(), Credentials.class);
        startActivity(intent);
    }
}