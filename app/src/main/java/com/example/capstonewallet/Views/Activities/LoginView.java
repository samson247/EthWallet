package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.capstonewallet.Views.Fragments.LoginFragment;
import com.example.capstonewallet.databinding.LoginBinding;

/**
 * The view class for the login activity, contains fragments that allow users to
 * login or create accounts
 */
public class LoginView extends AppCompatActivity  {
    private LoginBinding binding;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        //context = getApplicationContext();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment fragment = new LoginFragment();
        fragment.setFragmentManager(fragmentManager);
        fragmentTransaction.add(binding.container.getId(), fragment, null);
        fragmentTransaction.commit();
    }
}