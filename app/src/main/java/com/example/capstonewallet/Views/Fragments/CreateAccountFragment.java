package com.example.capstonewallet.Views.Fragments;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.capstonewallet.R;
import com.example.capstonewallet.viewmodels.CreateAccountViewModel;
import com.example.capstonewallet.Views.Activities.CredentialsView;
import com.example.capstonewallet.databinding.CreateAccountBinding;

import org.web3j.abi.datatypes.Bool;

import java.util.zip.Inflater;


//FIXME add existing account pathway and database table etc.
public class CreateAccountFragment extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener {
    private com.example.capstonewallet.databinding.LoginBinding binding;
    private EditText password;
    private EditText walletName;
    private EditText privateKey;
    private CreateAccountViewModel createAccountViewModel;
    private CreateAccountBinding createAccountBinding;
    private LayoutInflater inflater;
    private ViewGroup container;
    private CheckBox checkBoxExisting;
    private boolean addExistingChecked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        this.inflater = inflater;
        this.container = container;
        View view = inflater.inflate(R.layout.create_account, container, false);
        //binding = com.example.capstonewallet.databinding.LoginBinding.inflate(getLayoutInflater());
        createAccountBinding = CreateAccountBinding.inflate(getLayoutInflater());
        createAccountViewModel = new CreateAccountViewModel();

        final Button createAccountButton = view.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this::onClick);

        password = view.findViewById(R.id.editTextNewPassword);
        walletName = view.findViewById(R.id.editTextNewWalletName);
        privateKey = view.findViewById(R.id.editTextPrivateKey);
        checkBoxExisting = view.findViewById(R.id.checkboxExisting);
        checkBoxExisting.setOnCheckedChangeListener(this::onCheckedChanged);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       inflater.inflate(R.layout.add_existing_account, container);
        // super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), CredentialsView.class);
        if(!addExistingChecked) {
            Log.d("yo123", "piss?" + password.getText().toString());
            Log.d("yo123", "piss?" + createAccountBinding.editTextNewPassword.getText().toString());
            createAccountViewModel.setPassword(password.getText().toString());
            createAccountViewModel.setWalletName(walletName.getText().toString());
            createAccountViewModel.onClick(getContext(), addExistingChecked);
            Log.d("yo123", "Stop: " + createAccountViewModel.getPassword());
            Log.d("yo123", "Stop: " + createAccountViewModel.getFileName());
            intent.putExtra("credentials", new String[]{createAccountViewModel.getPassword(),
                                                            createAccountViewModel.getFileName()});
            //intent.putExtra("password", createAccountViewModel.getPassword());
            //intent.putExtra("fileName", createAccountViewModel.getFileName());
        }
        else if(addExistingChecked) {
            createAccountViewModel.setPassword(password.getText().toString());
            createAccountViewModel.setWalletName(walletName.getText().toString());
            createAccountViewModel.onClick(getContext(), addExistingChecked);
            intent.putExtra("credentials", new String[]{privateKey.getText().toString()});
            //add existing logic
            //put extras
        }
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            addExistingChecked = false;
        }
        else {
            addExistingChecked = false;
        }
    }
}