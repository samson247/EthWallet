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
import androidx.room.Database;

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
import java.util.Comparator;

public class AddressBookFragment extends Fragment implements View.OnClickListener {
    //recyclerview
    //button to add new contact
    //Alphabetically organized
    //Search feature
    private FloatingActionButton addContactButton;
    private ImageButton backArrow;
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    ArrayList<String> contacts = new ArrayList<>();
    ArrayList<String> addresses = new ArrayList<>();
    private AddressBookViewModel addressBookViewModel;
    private ArrayList<ContactEntity> contactEntities;
    private EditText searchBar;

    public AddressBookFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.address_book_fragment, container, false);


        addContactButton = (FloatingActionButton) view.findViewById(R.id.addContactButton);
        addContactButton.setOnClickListener(this::onClick);
        backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(this::onClick);
        backArrow.setVisibility(View.INVISIBLE);

        searchBar = view.findViewById(R.id.searchContacts);
        searchBar.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 adapter = null;
                 if(s.length() != 0) {
                     ArrayList<ContactEntity> sortedContactData = testSetContacts(s.toString());
                     adapter = new ContactsAdapter(getContext(), sortedContactData);
                 }
                 else {
                     adapter = new ContactsAdapter(getContext(), contactEntities);
                 }
                 adapter.setFragmentManager(getChildFragmentManager());
                 Log.d("yo123", "on textchanged");
                 recyclerView.swapAdapter(adapter, false);
                 recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        setAdapterData();
        sortContacts();
        //ContactFragment contactFragment = new ContactFragment("", "");
        adapter = new ContactsAdapter(getContext(), this.contactEntities);
        adapter.setFragmentManager(getChildFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.d("yo123", "oncreateview Address book Frag");
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == addContactButton.getId()) {
            addAddContactFragment("add", null, null);
        }
        else if(v.getId() == backArrow.getId()) {
            Log.d("yo123", "popping backstack");
            //getActivity().getFragmentManager().popBackStack("AddContact", 0);
            //getActivity().getSupportFragmentManager().popBackStackImmediate("AddContact", 0);
            this.getChildFragmentManager().popBackStack();
            backArrow.setVisibility(View.INVISIBLE);
        }
    }

    public void addAddContactFragment(String mode, String name, String address) {
        if(mode.equals("add")) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AddContactFragment addContactFragment = new AddContactFragment();
            fragmentTransaction.add(R.id.containerAddContact, addContactFragment, null);
            fragmentTransaction.addToBackStack("AddContact");
            fragmentTransaction.commit();
            backArrow.setVisibility(View.VISIBLE);
            addContactButton.setVisibility(View.INVISIBLE);
        }
        else if(mode.equals("edit")) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AddContactFragment addContactFragment = new AddContactFragment(name, address);
            fragmentTransaction.add(R.id.containerAddContact, addContactFragment, null);
            fragmentTransaction.addToBackStack("AddContact");
            fragmentTransaction.commit();
            backArrow.setVisibility(View.VISIBLE);
            addContactButton.setVisibility(View.INVISIBLE);
        }
        //((WalletView)getActivity()).getBottomNavigationFragment().isHidden();
    }

    public void setAdapterData() {
        if(contactEntities.size() > 0) {
            int i = 0;
            String name;
            String address;
            while(i < contactEntities.size()) {
                name = contactEntities.get(i).getName();
                address = contactEntities.get(i).getAddress();
                if(name != null) {
                    Log.d("yo123", "in set adapter" + name + " " + address);
                    //contacts.add(name);
                    //addresses.add(address);
                    contacts.add(String.valueOf(name.charAt(0)));
                    //addresses.add("");
                }
                else {
                    contactEntities.remove(i);
                }

                //contacts.add(contactEntities[i].getName());
                //Log.d("yo123", contactEntities[i].getName() + " " + contactEntities[i].getAddress());
                i++;
            }
            String previous = null;
            for(int index = 0; index < contacts.size(); index++) {
                if(!contacts.get(index).equals(previous)) {
                    ContactEntity contactEntity = new ContactEntity();
                    contactEntity.setName(contacts.get(index));
                    contactEntity.setAddress("");
                    contactEntities.add(contactEntity);
                    previous = contacts.get(index);
                }
            }
        }
    }

    public void sortContacts() {
        //Collections.sort(contacts, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(contactEntities, new Comparator<ContactEntity>() {
            @Override
            public int compare(ContactEntity contact1, ContactEntity contact2) {
                return contact1.getName().compareTo(contact2.getName());
            }
        });
    }

    public ArrayList<ContactEntity> testSetContacts(String substring) {
        int index = 0;
        ArrayList<ContactEntity> sortedContacts = new ArrayList<>();
        while(index < contactEntities.size()) {
                if(contactEntities.get(index).getName().toLowerCase().contains(substring) || contactEntities.get(index).getName().length() == 1) {
                    sortedContacts.add(contactEntities.get(index));
                }
                index++;
        }
        return sortedContacts;
    }

    public AddressBookViewModel getViewModel() {
        return this.addressBookViewModel;
    }

    public void editContact(String name, String address) {
        this.getChildFragmentManager().popBackStack();
        addAddContactFragment("edit", name, address);
    }
}
