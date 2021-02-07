package com.example.capstonewallet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.capstonewallet.databinding.FragmentTransactionBinding;

public class AddressBookFragment extends Fragment {
    //recyclerview
    //button to add new contact
    //Alphabetically organized
    //Search feature

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.address_book_fragment, container, false);



        //fragmentManager = getSupportFragmentManager();
        //fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        //loginButton.setOnClickListener(this::onClick);
        //final Button createAccountButton = (Button) view.findViewById(fragmentBinding.createAccountButton.getId());
        //createAccountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "oncreateview Address book Frag");
        return view;
    }
}
