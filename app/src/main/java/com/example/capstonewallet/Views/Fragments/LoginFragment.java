package com.example.capstonewallet.Views.Fragments;

import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.capstonewallet.viewmodels.LoginViewModel;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.databinding.LoginFragmentBinding;


/**
 * Fragment class that allows user to login
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private LoginViewModel loginViewModel;
    private LoginFragmentBinding fragmentBinding;
    private FragmentManager fragmentManager;
    private EditText password;
    private EditText address;
    private ProgressBar progressBar;
    private Button loginButton;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        fragmentBinding = LoginFragmentBinding.inflate(getLayoutInflater());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginButton = view.findViewById(fragmentBinding.loginButton.getId());
        loginButton.setOnClickListener(this::onClick);

        final TextView createAccountTextView = (TextView) view.findViewById(fragmentBinding.createOrAddTextView.getId());
        createAccountTextView.setOnClickListener(this::onClick);

        progressBar = view.findViewById(fragmentBinding.progressBar.getId());

        password = (EditText) view.findViewById(R.id.editTextTextPersonName2);
        address = (EditText) view.findViewById(R.id.editTextTextPersonName);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.settingsItems, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        Log.d("yo123", "oncreateview");
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d("yo123", "inonclick");
        if(v.getId() == fragmentBinding.loginButton.getId()) {
            loginViewModel.setAddress(address.getText().toString());
            loginViewModel.setPassword(password.getText().toString());
            boolean proceed = loginViewModel.onClick(getContext());

            if(proceed) {
                loginButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                Intent intent = new Intent(getActivity(), WalletView.class);
                // pass credentials
                intent.putExtra("password", loginViewModel.getPassword());
                intent.putExtra("fileName", loginViewModel.getFileName());
                startActivity(intent);
            }
            else {
                password.setText("");
                password.setHint("Invalid Password");
                address.setHintTextColor(Color.RED);
                password.setHintTextColor(Color.RED);
            }
        }
        else if (v.getId() == fragmentBinding.createOrAddTextView.getId()){
            if(fragmentManager.findFragmentById(R.id.login_fragment) != null) {
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.login_fragment)).commit();
            }

            CreateAccountFragment fragment = new CreateAccountFragment();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }
}