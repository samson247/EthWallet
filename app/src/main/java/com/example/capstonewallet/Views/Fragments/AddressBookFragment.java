package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddressBookFragment extends Fragment implements View.OnClickListener {
    //recyclerview
    //button to add new contact
    //Alphabetically organized
    //Search feature
    private FloatingActionButton createAccountButton;

    public AddressBookFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.address_book_fragment, container, false);


        createAccountButton = (FloatingActionButton) view.findViewById(R.id.addContactButton);
        createAccountButton.setOnClickListener(this::onClick);

        Log.d("yo123", "oncreateview Address book Frag");
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == createAccountButton.getId()) {
            addAddContactFragment();
        }
    }

    public void addAddContactFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddContactFragment addContactFragment = new AddContactFragment(getContext());
        fragmentTransaction.add(R.id.containerTransactionList, addContactFragment, null);
        fragmentTransaction.commit();
    }
}
