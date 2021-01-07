package com.example.capstonewallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CreateAccount extends Fragment implements View.OnClickListener {
    private WalletModel walletModel;

    public CreateAccount(Context context) {
        super(R.layout.create_account);
        walletModel = new WalletModel(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.create_account, container, false);
        Button one = (Button) view.findViewById(R.id.createAccountButton);
        one.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        walletModel.createWallet("password");
        Intent intent = new Intent(getActivity(), WalletView.class);
        startActivity(intent);
    }
}