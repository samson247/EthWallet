package com.example.capstonewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.capstonewallet.databinding.LoginBinding;

public class LoginView extends AppCompatActivity implements View.OnClickListener {
    private LoginViewModel loginViewModel;
    private WalletView walletView;
    private KeyEvent.Callback loginCallback;
    private LoginBinding binding;
    private AccountRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        walletView = new WalletView();
        repository = new AccountRepository(getApplicationContext());
        binding.loginButton.setOnClickListener(this::onClick);
        binding.createAccountButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton) {
            loginViewModel.setPublicKey(binding.editTextTextPersonName.getText().toString());
            loginViewModel.setPassword(binding.editTextTextPersonName2.getText().toString());
            loginViewModel.onClick(repository);
        }
        else if (v.getId() == R.id.createAccountButton){
            walletView.setVisible(true);
        }
    }
}