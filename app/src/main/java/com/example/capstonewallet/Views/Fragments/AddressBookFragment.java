package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Database.ContactEntity;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.Views.Adapters.ContactsAdapter;
import com.example.capstonewallet.Views.Adapters.TransactionListAdapter;
import com.example.capstonewallet.viewmodels.AddressBookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class AddressBookFragment extends Fragment implements View.OnClickListener {
    //recyclerview
    //button to add new contact
    //Alphabetically organized
    //Search feature
    private FloatingActionButton createAccountButton;
    private ImageButton backArrow;
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    ArrayList<String> contacts = new ArrayList<>();
    private AddressBookViewModel addressBookViewModel;
    private ContactEntity[] contactEntities;
    private EditText searchBar;

    public AddressBookFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.address_book_fragment, container, false);


        createAccountButton = (FloatingActionButton) view.findViewById(R.id.addContactButton);
        createAccountButton.setOnClickListener(this::onClick);
        backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(this::onClick);

        searchBar = view.findViewById(R.id.searchContacts);
        searchBar.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 if(s.length() != 0) {
                     testSetContacts();
                     adapter = new ContactsAdapter(getContext(), contacts);
                     Log.d("yo123", "on textchanged");
                     recyclerView.setAdapter(adapter);
                     recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                 }

             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

        recyclerView = view.findViewById(R.id.addressBookRecycler);
        //contacts.add("Johnny Walker");
        //contacts.add("Tommy Walker");
        //contacts.add("Bobby Walker");
        addressBookViewModel = new AddressBookViewModel(getContext());
        contactEntities = addressBookViewModel.getContacts();
        setContacts();
        sortContacts();
        //ContactFragment contactFragment = new ContactFragment("", "");
        adapter = new ContactsAdapter(getContext(), this.contacts);
        adapter.setFragmentManager(getChildFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.d("yo123", "oncreateview Address book Frag");
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == createAccountButton.getId()) {
            addAddContactFragment();
        }
        else if(v.getId() == backArrow.getId()) {
            Log.d("yo123", "popping backstack");
            //getActivity().getFragmentManager().popBackStack("AddContact", 0);
            //getActivity().getSupportFragmentManager().popBackStackImmediate("AddContact", 0);
            this.getChildFragmentManager().popBackStack();
        }
    }

    public void addAddContactFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddContactFragment addContactFragment = new AddContactFragment(getActivity().getBaseContext());
        fragmentTransaction.add(R.id.containerAddContact, addContactFragment, null);
        fragmentTransaction.addToBackStack("AddContact");
        fragmentTransaction.commit();
    }

    public void setContacts() {
        if(contactEntities.length > 0) {
            int i = 0;
            String name;
            while(i < contactEntities.length) {
                name = contactEntities[i].getName();
                if(name != null){
                    contacts.add(name);
                    contacts.add(String.valueOf(name.charAt(0)));
                }
                //contacts.add(contactEntities[i].getName());
                Log.d("yo123", contactEntities[i].getName() + " " + contactEntities[i].getAddress());
                i++;
            }
        }
    }

    public void sortContacts() {
        Collections.sort(contacts, String.CASE_INSENSITIVE_ORDER);
    }

    public void testSetContacts() {
        contacts.clear();
        contacts.add("Jimbo Walker");
        contacts.add("Rocko Flocker");
        contacts.add("Jimbo Wheeler");
    }
}
