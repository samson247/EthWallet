package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.CreateAccountFragment;
import com.example.capstonewallet.Views.Fragments.LoginFragment;
import com.example.capstonewallet.databinding.LoginBinding;

/**
 * The view class for the login activity, contains fragments that allow users to
 * login or create accounts
 */
public class LoginView extends AppCompatActivity  {
    private LoginBinding binding;
    private LoginFragment loginFragment;
    private CreateAccountFragment createAccountFragment;

    /**
     * Creates activity view and initializes components
     * @param savedInstanceState an optional bundle to load saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        createAccountFragment = new CreateAccountFragment();
        loginFragment = new LoginFragment();
        loginFragment.setFragmentManager(fragmentManager);
        loginFragment.setArguments(bundle);
        fragmentTransaction.add(binding.container.getId(), loginFragment, null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        switchFragments();
        return true;
    }

    public void switchFragments() {
        if(loginFragment.isHidden()) {
            getSupportFragmentManager().beginTransaction().hide(createAccountFragment).commit();
            getSupportFragmentManager().beginTransaction().show(loginFragment).commit();
        }
        else {
            if(!createAccountFragment.isAdded()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportFragmentManager().beginTransaction().hide(loginFragment).commit();
                getSupportFragmentManager().beginTransaction().add(binding.container.getId(),
                        createAccountFragment, null).commit();
            }
            else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportFragmentManager().beginTransaction().hide(loginFragment).commit();
                getSupportFragmentManager().beginTransaction().show(createAccountFragment).commit();
            }
        }
    }
}