package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
import com.example.capstonewallet.viewmodels.AddressBookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

/**
 * Class representing view of the address book fragment
 */
public class AddressBookFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton addContactButton;
    private ImageButton backArrow;
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    ArrayList<String> contacts = new ArrayList<>();
    private AddressBookViewModel addressBookViewModel;
    private ArrayList<ContactEntity> contactEntities;
    private EditText searchBar;
    private LinearLayout popupContainer;

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.address_book_fragment, container, false);

        ((WalletView)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        addContactButton = view.findViewById(R.id.addContactButton);
        addContactButton.setOnClickListener(this::onClick);
        backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(this::onClick);
        backArrow.setVisibility(View.INVISIBLE);
        searchBar = view.findViewById(R.id.searchContacts);
        searchBar.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

             // Sorts contacts when text of search bar is changed
             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 Fragment fragment = adapter.getFragment();
                 adapter = null;
                 if(s.length() != 0) {
                     if(fragment != null) {
                         getChildFragmentManager().beginTransaction().remove(fragment).commit();
                     }
                     ArrayList<ContactEntity> sortedContactData = addressBookViewModel.setContacts(contactEntities, s.toString());
                     adapter = new ContactsAdapter(getContext(), sortedContactData);
                 }
                 else {
                     adapter = new ContactsAdapter(getContext(), contactEntities);
                 }
                 adapter.setFragmentManager(getChildFragmentManager());
                 recyclerView.swapAdapter(adapter, false);
                 recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
             }

             @Override
             public void afterTextChanged(Editable s) {}
         });

        // Recycler view is initialized with contacts
        recyclerView = view.findViewById(R.id.addressBookRecycler);
        addressBookViewModel = new AddressBookViewModel(getContext());
        contactEntities = addressBookViewModel.getContacts();
        setAdapterData();
        addressBookViewModel.sortContacts(contactEntities);
        adapter = new ContactsAdapter(getContext(), this.contactEntities);
        adapter.setFragmentManager(getChildFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        popupContainer = view.findViewById(R.id.popupContainer);
        return view;
    }

    /**
     * Handles click events
     * @param v the view for this class
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == addContactButton.getId()) {
            Fragment fragment = adapter.getFragment();
            if(fragment != null) {
                getChildFragmentManager().beginTransaction().remove(fragment).commit();
            }
            addAddContactFragment("add", null, null);
        }
        else if(v.getId() == backArrow.getId()) {
            this.getChildFragmentManager().popBackStack();
            backArrow.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Adds the add contact fragment
     * @param mode either add or edit contact mode
     * @param name the name of contact
     * @param address the address of contact
     */
    public void addAddContactFragment(String mode, String name, String address) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(mode.equals("add")) {
            AddContactFragment addContactFragment = new AddContactFragment();
            fragmentTransaction.add(R.id.containerAddContact, addContactFragment, null);
            fragmentTransaction.addToBackStack("AddContact");
        }
        else if(mode.equals("edit")) {
            AddContactFragment editContactFragment = new AddContactFragment(name, address);
            fragmentTransaction.add(R.id.containerAddContact, editContactFragment, null);
            fragmentTransaction.addToBackStack("EditContact");
        }
        //fragmentTransaction.setCustomAnimations(R.anim.slide_over, R.anim.fade_out);
        fragmentTransaction.commit();
        addContactButton.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the data in adapter to put in recycler view
     */
    public void setAdapterData() {
        if(contactEntities.size() > 0) {
            int index = 0;
            String name;
            String address;
            // Adds a recycler element for each letter present in contact names
            while(index < contactEntities.size()) {
                name = contactEntities.get(index).getName();
                address = contactEntities.get(index).getAddress();
                if(name != null) {
                    if(!contacts.contains(String.valueOf(name.charAt(0)))) {
                        contacts.add(String.valueOf(name.charAt(0)));
                    }
                }
                else {
                    contactEntities.remove(index);
                }
                index++;
            }

            // Removes duplicate contacts if present
            String previous = null;
            for(int index2 = 0; index2 < contacts.size(); index2++) {
                if(!contacts.get(index2).equals(previous)) {
                    ContactEntity contactEntity = new ContactEntity();
                    contactEntity.setName(contacts.get(index2));
                    contactEntity.setAddress("");
                    contactEntities.add(contactEntity);
                    previous = contacts.get(index2);
                }
            }
        }
    }

    /**
     * Calls edit contact in view model class
     * @param name name of contact to edit
     * @param address address of contact to edit
     */
    public void editContact(String name, String address) {
        this.getChildFragmentManager().popBackStack();
        addAddContactFragment("edit", name, address);
    }

    /**
     * Calls delete contact in view model class
     * @param name name of contact to delete
     * @param address address of contact to delete
     */
    public void deleteContact(String name, String address) {
        addressBookViewModel.deleteContact(name, address);
        reloadContacts();
        getChildFragmentManager().popBackStack();
    }

    /**
     * Removes the add contact fragment and reloads recycler view
     */
    public void popAddContactFragment() {
        this.getChildFragmentManager().popBackStack();
        addContactButton.setVisibility(View.VISIBLE);
        reloadContacts();
    }

    /**
     * Reloads contacts recycler view when a contact has been edited or removed
     */
    public void reloadContacts() {
        contactEntities = addressBookViewModel.getContacts();
        setAdapterData();
        addressBookViewModel.sortContacts(contactEntities);
        adapter = null;
        adapter = new ContactsAdapter(getContext(), contactEntities);
        adapter.setFragmentManager(getChildFragmentManager());
        recyclerView.swapAdapter(adapter, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
