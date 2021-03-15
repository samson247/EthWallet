package com.example.capstonewallet;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.Database.ContactEntity;
import com.example.capstonewallet.Views.Adapters.TransactionListAdapter;
import com.example.capstonewallet.viewmodels.AddContactViewModel;

import java.time.Duration;

public class AddContactFragment extends Fragment implements View.OnClickListener {
    private Button addContactButton;
    private EditText name;
    private EditText address;
    private String editName = null;
    private String editAddress = null;
    private AccountRepository repository;
    private ImageButton backArrow;
    private AddContactViewModel addContactViewModel;

    public AddContactFragment() {

    }

    public AddContactFragment(String name, String address) {
        this.editName = name;
        this.editAddress = address;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        //FIXME context from container test
        Context context = container.getContext();
        addContactViewModel = new AddContactViewModel(context);
        View view = inflater.inflate(R.layout.add_contact_fragment, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);

        name = view.findViewById(R.id.addContactName);
        address = view.findViewById(R.id.addContactAddress);
        addContactButton = view.findViewById(R.id.addContactAddButton);
        addContactButton.setOnClickListener(this::onClick);

        if(!(editName == null)) {
            name.setText(editName);
            address.setText(editAddress);
            addContactButton.setText("Edit Contact");
        }

       // addContactButton = view.findViewById(R.id.addContactAddButton);
        //addContactButton.setOnClickListener(this::onClick);


        //repository = new AccountRepository(getContext());
        /*ContactEntity[] contacts = repository.getContacts();
        if(contacts.length > 0) {
            int i = 0;
            while(i < contacts.length) {
                Log.d("yo123", contacts[i].getName() + " " + contacts[i].getAddress());
                i++;
            }
        }*/
        //Log.d("yo123", repository.getContacts().toString());
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
        if(v.getId() == addContactButton.getId() && addContactButton.getText().toString().equals("Add Contact")) {
            if(addContactViewModel.onClick(name.getText().toString(), address.getText().toString())) {
                Toast.makeText(getActivity().getApplicationContext(), "Contact successfully created", Toast.LENGTH_SHORT).show();
            }
            else {
                name.setText("");
                name.setHintTextColor(Color.RED);
                Toast.makeText(getActivity().getApplicationContext(), "Name must be between 1 and 20 characters", Toast.LENGTH_SHORT);
            }
        }
        else if(v.getId() == addContactButton.getId() && addContactButton.getText().toString().equals("Edit Contact")) {
            addContactViewModel.deleteEditedContact(editName, editAddress);
            if(addContactViewModel.onClick(name.getText().toString(), address.getText().toString())) {
                Toast.makeText(getActivity().getApplicationContext(), "Contact successfully edited", Toast.LENGTH_SHORT).show();
            }
            else {
                name.setText("");
                name.setHintTextColor(Color.RED);
                Toast.makeText(getActivity().getApplicationContext(), "Name must be between 1 and 20 characters", Toast.LENGTH_SHORT);
            }
        }
        else if(v.getId() == backArrow.getId()) {
            Log.d("yo123", "popping backstack");
            getActivity().getFragmentManager().popBackStack();
        }
    }
}

