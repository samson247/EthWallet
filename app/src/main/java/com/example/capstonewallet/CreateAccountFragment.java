package com.example.capstonewallet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.capstonewallet.viewmodels.CreateAccountViewModel;
import com.example.capstonewallet.Views.Activities.CredentialsView;
import com.example.capstonewallet.databinding.CreateAccountBinding;

import java.util.zip.Inflater;


public class CreateAccountFragment extends Fragment implements View.OnClickListener {
    private com.example.capstonewallet.databinding.LoginBinding binding;
    private EditText password;
    private EditText walletName;
    private CreateAccountViewModel createAccountViewModel;
    private CreateAccountBinding createAccountBinding;
    private LayoutInflater inflater;
    private ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        this.inflater = inflater;
        this.container = container;
        View view = inflater.inflate(R.layout.create_account, container, false);
        //binding = com.example.capstonewallet.databinding.LoginBinding.inflate(getLayoutInflater());
        createAccountBinding = CreateAccountBinding.inflate(getLayoutInflater());
        createAccountViewModel = new CreateAccountViewModel();

        final Button createAccountButton = (Button) view.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this::onClick);

        password = (EditText) view.findViewById(R.id.editTextNewPassword);
        walletName = (EditText) view.findViewById(R.id.editTextNewWalletName);

        RadioGroup newAccountOptions = (RadioGroup) view.findViewById(R.id.radioGroup);
        newAccountOptions.setOnCheckedChangeListener(this::onCheckedChanged);

        return view;
    }

    private void onCheckedChanged(RadioGroup radioGroup, int i) {
        // FIXME load fragment using loginmodel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       inflater.inflate(R.layout.add_existing_account, container);
        // super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Log.d("yo123", "piss?" + password.getText().toString());
        Log.d("yo123", "piss?" + createAccountBinding.editTextNewPassword.getText().toString());
        createAccountViewModel.setPassword(password.getText().toString());
        createAccountViewModel.setFileName(walletName.getText().toString());
        createAccountViewModel.onClick(getContext());


        Intent intent = new Intent(getActivity(), CredentialsView.class);

        Log.d("yo123", "Stop: " + createAccountViewModel.getPassword());
        Log.d("yo123", "Stop: " + createAccountViewModel.getFileName());
        intent.putExtra("password", createAccountViewModel.getPassword());
        intent.putExtra("fileName", createAccountViewModel.getFileName());
        startActivity(intent);
    }
}