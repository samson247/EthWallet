package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.capstonewallet.ViewModels.LoginViewModel;
import com.example.capstonewallet.Views.Fragments.LoginFragment;
import com.example.capstonewallet.databinding.LoginBinding;

public class LoginView extends AppCompatActivity  {
    private LoginViewModel loginViewModel;
    //private WalletView walletView;
    private LoginBinding binding;
    //private Fragment createAccount;
    //private Fragment login;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("yo123", "going");
        super.onCreate(savedInstanceState);
        Log.d("yo123", "going");
        binding = LoginBinding.inflate(getLayoutInflater());
        Log.d("yo123", "back");
        View view = binding.getRoot();
        Log.d("yo123", "back");
        setContentView(view);
        Log.d("yo123", "to");

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        Log.d("yo123", "Cali");
        //walletView = new WalletView();
        context = getApplicationContext();
        Log.d("yo123", "going");
        //repository = new AccountRepository(getApplicationContext());
        //binding.loginButton.setOnClickListener(this::onClick);
        Log.d("yo123", "going");
        //binding.createAccountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "back");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment fragment = new LoginFragment();
        fragment.setFragmentManager(fragmentManager);
        fragment.setFragmentTransaction(fragmentTransaction);
        fragmentTransaction.add(binding.container.getId(), fragment, "");
        fragmentTransaction.commit();
    }

    /*
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton) {
            loginViewModel.setPublicKey(binding.editTextTextPersonName.getText().toString());
            loginViewModel.setPassword(binding.editTextTextPersonName2.getText().toString());
            loginViewModel.onClick(context);
        }
        else if (v.getId() == R.id.createAccountButton){
            //binding.fragmentContainerView.setVisibility(View.VISIBLE);
           // binding.loginButton.setVisibility(View.INVISIBLE);
           // binding.createAccountButton.setVisibility(View.INVISIBLE);
            /*createAccount = new CreateAccount(getApplicationContext());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(binding.fragmentContainerView, createAccount)
                    .commit();
        }
    } */
}