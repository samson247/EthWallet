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

public class LoginView extends AppCompatActivity {
    private LoginViewModel model;
    private KeyEvent.Callback loginCallback;
    private LoginBinding binding;
    private AccountRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        model = new ViewModelProvider(this).get(LoginViewModel.class);
        repository = new AccountRepository(getApplicationContext());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setPublicKey(binding.editTextTextPersonName.getText().toString());
                model.setPassword(binding.editTextTextPersonName2.getText().toString());
                model.onClick(v, model, repository);
            }
        });
    }
}