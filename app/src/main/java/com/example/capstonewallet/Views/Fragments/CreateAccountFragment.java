package com.example.capstonewallet.Views.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.LoginView;
import com.example.capstonewallet.viewmodels.CreateAccountViewModel;
import com.example.capstonewallet.Views.Activities.GettingStartedView;
import com.example.capstonewallet.databinding.CreateAccountBinding;

/**
 * Fragment class for creating a new wallet account
 */
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
    private CheckBox checkBoxExisting2;
    private ImageButton infoButton;
    private Button createAccountButton;
    private boolean addExistingChecked = false;
    private ProgressBar progressBar;

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        this.inflater = inflater;
        this.container = container;
        View view = inflater.inflate(R.layout.create_account, container, false);
        createAccountBinding = CreateAccountBinding.inflate(getLayoutInflater());
        createAccountViewModel = new CreateAccountViewModel();

        ((LoginView)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createAccountButton = view.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this::onClick);
        password = view.findViewById(R.id.editTextNewPassword);
        walletName = view.findViewById(R.id.editTextNewWalletName);
        privateKey = view.findViewById(R.id.editTextPrivateKey);
        checkBoxExisting = view.findViewById(R.id.checkboxExisting);
        checkBoxExisting.setOnCheckedChangeListener(this::onCheckedChanged);
        checkBoxExisting2 = view.findViewById(R.id.checkboxExisting2);
        checkBoxExisting2.setOnCheckedChangeListener(this::onCheckedChanged);
        infoButton = view.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(this::onClick);
        progressBar = view.findViewById(R.id.progressBar2);

        return view;
    }

    /**
     * Called after view is created, allows for null checking on components
     * @param view the fragment class's view
     * @param savedInstanceState the optional saved instance state bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       inflater.inflate(R.layout.add_existing_account, container);
    }

    /**
     * Responds to click events for the fragment class's buttons
     * @param v the fragment class's view
     */
    @Override
    public void onClick(View v) {
        // Displays valid password criteria
        if(v.getId() == infoButton.getId()) {
            new AlertDialog.Builder(getContext(), R.style.AlertDialog).setMessage("Password must be at least 7 characters long and contain a number.").show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            createAccountButton.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(getActivity(), GettingStartedView.class);
            // Adds new wallet account and creates Ethereum account
            if(!addExistingChecked) {
                createAccountViewModel.setPassword(password.getText().toString());
                createAccountViewModel.setWalletName(walletName.getText().toString());
                int proceed = createAccountViewModel.onClick(getContext(), addExistingChecked);
                if(proceed == -1) {
                    intent.putExtra("credentials", new String[]{createAccountViewModel.getPassword(),
                            createAccountViewModel.getFileName()});

                    startActivity(intent);
                }
                else if(proceed == 0 || proceed == 2) {
                    showInvalidName();
                }
                else {
                    showInvalidPassword();
                }
            }
            // Adds an existing Ethereum account to a new wallet account
            else if(addExistingChecked) {
                createAccountViewModel.setPassword(password.getText().toString());
                createAccountViewModel.setWalletName(walletName.getText().toString());
                int proceed = createAccountViewModel.onClick(getContext(), addExistingChecked);
                if(proceed == -1) {
                    intent.putExtra("credentials", new String[]{createAccountViewModel.getPassword(), createAccountViewModel.getFileName()});
                    startActivity(intent);
                }
                else if(proceed == 0 || proceed == 2){
                    showInvalidName();
                }
                else {
                    showInvalidPassword();
                }
            }
        }
    }

    // Toggles private key field visibility that is necessary only when adding existing account
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            addExistingChecked = true;
            privateKey.setVisibility(View.VISIBLE);
            checkBoxExisting.setVisibility(View.INVISIBLE);
            checkBoxExisting2.setVisibility(View.VISIBLE);
            checkBoxExisting2.setChecked(true);
        }
        else {
            addExistingChecked = false;
            privateKey.setVisibility(View.INVISIBLE);
            checkBoxExisting.setVisibility(View.VISIBLE);
            checkBoxExisting2.setVisibility(View.INVISIBLE);
            checkBoxExisting.setChecked(false);
        }
    }

    /**
     * Shows user name is invalid
     */
    public void showInvalidName() {
        walletName.setText("");
        walletName.setHint("Name already exists");
        walletName.setHintTextColor(Color.RED);
    }

    /**
     * Shows user password is invalid
     */
    public void showInvalidPassword() {
        password.setText("");
        password.setHint("Invalid password");
        password.setHintTextColor(Color.RED);
    }
}