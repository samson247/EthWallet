package com.example.capstonewallet;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.Views.Fragments.AddressBookFragment;
import com.example.capstonewallet.viewmodels.AddContactViewModel;

/**
 * Fragment class for add contact
 *
 * @author Sam Dodson
 */
public class AddContactFragment extends Fragment implements View.OnClickListener {
    private Button addContactButton;
    private TextView addContactHeading;
    private EditText name;
    private EditText address;
    private String editName = null;
    private String editAddress = null;
    private AddContactViewModel addContactViewModel;

    /**
     * Constructor used when adding contact
     */
    public AddContactFragment() {}

    /**
     * Contact used when editing contact
     * @param name name of contact to edit
     * @param address address of contact to edit
     */
    public AddContactFragment(String name, String address) {
        this.editName = name;
        this.editAddress = address;
    }

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        Context context = container.getContext();
        addContactViewModel = new AddContactViewModel(context);
        View view = inflater.inflate(R.layout.add_contact_fragment, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);

        ((WalletView)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = view.findViewById(R.id.addContactName);
        address = view.findViewById(R.id.addContactAddress);
        addContactButton = view.findViewById(R.id.addContactAddButton);
        addContactButton.setOnClickListener(this::onClick);
        addContactHeading = view.findViewById(R.id.addContactHeading);

        if(editName != null) {
            name.setText(editName);
            address.setText(editAddress);
            addContactHeading.setText("Edit Contact");
        }
        return view;
    }

    /**
     * Handles click events to either add or edit contact
     * @param v the component that created click event
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == addContactButton.getId() && addContactButton.getText().toString().equals("Add Contact")) {
            // Calls view model to add contact
            if(addContactViewModel.addContact(name.getText().toString(), address.getText().toString())) {
                Toast.makeText(getActivity().getApplicationContext(), "Contact successfully created", Toast.LENGTH_SHORT).show();
                ((AddressBookFragment)getParentFragment()).popAddContactFragment();
            }
            else {
                name.setText("");
                name.setHintTextColor(Color.RED);
                Toast.makeText(getActivity().getApplicationContext(), "Name must be between 1 and 20 characters", Toast.LENGTH_SHORT);
            }
        }
        else if(v.getId() == addContactButton.getId() && addContactButton.getText().toString().equals("Edit Contact")) {
            // Calls view model to edit contact
            if(addContactViewModel.editContact(editName, editAddress, name.getText().toString(), address.getText().toString())) {
                Toast.makeText(getActivity().getApplicationContext(), "Contact successfully edited", Toast.LENGTH_SHORT).show();
                ((AddressBookFragment)getParentFragment()).popAddContactFragment();
            }
            else {
                name.setText("");
                name.setHintTextColor(Color.RED);
                Toast.makeText(getActivity().getApplicationContext(), "Name must be between 1 and 20 characters", Toast.LENGTH_SHORT);
            }
        }
    }
}

