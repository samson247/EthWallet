package com.example.capstonewallet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddContactFragment extends Fragment implements View.OnClickListener {
    private Button addContactButton;
    private EditText name;
    private EditText address;
    private AccountRepository repository;

    public AddContactFragment(Context context) {
        repository = new AccountRepository(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        //FIXME context from container test
        Context context = container.getContext();
        View view = inflater.inflate(R.layout.add_contact_fragment, container, false);

        name = (EditText) view.findViewById(R.id.addContactName);
        address = (EditText) view.findViewById(R.id.addContactAddress);
        addContactButton = (Button) view.findViewById(R.id.addContactAddButton);
        addContactButton.setOnClickListener(this::onClick);

        //fragmentManager = getSupportFragmentManager();
        //fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        //loginButton.setOnClickListener(this::onClick);
        //final Button createAccountButton = (Button) view.findViewById(fragmentBinding.createAccountButton.getId());
        //createAccountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "oncreateview Add contact Frag");
        return view;
    }

    // Move repository to wallet model class
    @Override
    public void onClick(View v) {
        if(v.getId() == addContactButton.getId()) {
            Log.d("yo123", repository.getAccountFile("0x87d2201493f2764dd1b70449fde732d01c9d864a"));
            ContactEntity contactEntity = new ContactEntity();
            contactEntity.setName("Test");
            //Log.d("yo123", repository.getAccountFile("Test"));
            contactEntity.setAddress("0x87d2201493f2764dd1b70449fde732d01c9d864a");
            //Log.d("yo123", this.address.getText().toString());
            repository.insertContact(contactEntity);
            Log.d("yo123", "first contact" + repository.getContactAddress("Test"));
        }
    }
}

