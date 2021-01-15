package com.example.capstonewallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CreateAccountFragment extends Fragment implements View.OnClickListener {
    private WalletModel walletModel;
    private com.example.capstonewallet.databinding.LoginBinding binding;
    private EditText password;
    private EditText publicKey;

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

        //binding.editTextTextPersonName2.getText().toString();
        password = (EditText) view.findViewById(R.id.editTextTextPersonName3);
        publicKey = (EditText) view.findViewById(R.id.editTextTextPersonName);

        return view;
    }

    @Override
    public void onClick(View v) {
        getInstance(getContext());

        //work on 2 way binding
        //walletModel.createWallet(binding.editTextTextPersonName2.getText().toString());
        //EditText password = (EditText) v.findViewById(R.id.editTextTextPersonName2);
        //EditText publicKey = (EditText) v.findViewById(R.id.editTextTextPersonName);

        walletModel.createWallet(password.getText().toString());
        //popup showing credentials and telling them to take note
        Intent intent = new Intent(getActivity(), CredentialsView.class);

        //String[] credentialArray = new String[] {binding.editTextTextPersonName2.getText().toString(), binding.editTextTextPersonName.getText().toString()};

        // get public key from wallet object
        String[] credentialArray = new String[] {password.getText().toString(), walletModel.getPublicKey().toString(16)};

        intent.putExtra("credentials", credentialArray);
        startActivity(intent);
    }
}