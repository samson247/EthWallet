package com.example.capstonewallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstonewallet.databinding.FragmentBottomNavigationBinding;
import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.LoginFragmentBinding;



public class BottomNavigationFragment extends Fragment implements View.OnClickListener {
    private FragmentBottomNavigationBinding binding;
    private FragmentManager fragmentManager;

    /*
    public LoginFragment getInstance(Context context) {
        //super(R.layout.create_account);
        //walletModel = new WalletModel(context);
        return this;
    }*/

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
        binding = FragmentBottomNavigationBinding.inflate(getLayoutInflater());
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        //loginButton.setOnClickListener(this::onClick);
        //final Button createAccountButton = (Button) view.findViewById(fragmentBinding.createAccountButton.getId());
        //createAccountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "oncreateview");
        return view;
    }


    @Override
    public void onClick(View v) {
        Log.d("yo123", "inonclick");
        if(v.getId() == binding.button2.getId()) {
            if(fragmentManager.findFragmentById(R.id.login_fragment) != null) {
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.transaction)).commit();
            }
            //fragman.beginTransaction().remove(fragman.findFragmentById(R.id.fraglog)).commit();
            CreateAccountFragment fragment = new CreateAccountFragment();
            fragmentManager.beginTransaction().replace(R.id.container_top, fragment).commit();
        }
        else if(v.getId() == binding.button3.getId()) {

        }
        else if(v.getId() == binding.button4.getId()) {

        }
    }
}