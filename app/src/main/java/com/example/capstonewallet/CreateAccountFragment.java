package com.example.capstonewallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.capstonewallet.databinding.CreateAccountBinding;


public class CreateAccountFragment extends Fragment implements View.OnClickListener {
    private WalletModel walletModel;
    private com.example.capstonewallet.databinding.LoginBinding binding;
    private EditText password;
    private EditText publicKey;
    private CreateAccountViewModel createAccountViewModel;
    private CreateAccountBinding createAccountBinding;
    //private com.example.capstonewallet.databinding.CreateAccountFragmentBinding binding2;

    public CreateAccountFragment getInstance(Context context) {
        //super(R.layout.create_account);
        walletModel = new WalletModel(context);
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.create_account, container, false);
        //binding = com.example.capstonewallet.databinding.LoginBinding.inflate(getLayoutInflater());
        createAccountBinding = CreateAccountBinding.inflate(getLayoutInflater());
        createAccountViewModel = new CreateAccountViewModel();

        final Button createAccountButton = (Button) view.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this::onClick);

        //binding.editTextTextPersonName2.getText().toString();
        password = (EditText) view.findViewById(R.id.editTextNewPassword);
        //password = createAccountBinding.editTextTextPersonName3.getText().toString();
        //createAccountViewModel.setPassword(password.getText().toString());
       // publicKey = (EditText) view.findViewById(R.id.editTextTextPersonName);

        return view;
    }

    @Override
    public void onClick(View v) {
        getInstance(getContext());
        //cavm instamce call onclick with context
        //createAccountBinding = CreateAccountBinding.inflate(getLayoutInflater());
        //password = (EditText) v.findViewById(R.id.editTextTextPersonName3);
        Log.d("yo123", "piss?" + password.getText().toString());
        Log.d("yo123", "piss?" + createAccountBinding.editTextNewPassword.getText().toString());
        createAccountViewModel.setPassword(password.getText().toString());
        createAccountViewModel.onClick(getContext());
        //work on 2 way binding
        //walletModel.createWallet(binding.editTextTextPersonName2.getText().toString());
        //EditText password = (EditText) v.findViewById(R.id.editTextTextPersonName2);
        //EditText publicKey = (EditText) v.findViewById(R.id.editTextTextPersonName);

        //walletModel.createWallet(password.getText().toString());
        //popup showing credentials and telling them to take note
        Intent intent = new Intent(getActivity(), CredentialsView.class);

        //String[] credentialArray = new String[] {binding.editTextTextPersonName2.getText().toString(), binding.editTextTextPersonName.getText().toString()};

        // get public key from wallet object
        Log.d("yo123", "Stop: " + createAccountViewModel.getPassword());
        Log.d("yo123", "Stop: " + createAccountViewModel.getFileName());
        String[] credentialArray = new String[] {createAccountViewModel.getPassword(), createAccountViewModel.getFileName()};
        //intent.getParcelableExtra(walletModel);
        intent.putExtra("credentials", credentialArray);
        startActivity(intent);
    }
}