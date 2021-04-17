package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.capstonewallet.R;

//TODO close popup when filtering contacts
/**
 * Fragment class for contact popup
 *
 * @author Sam Dodson
 */
public class ContactFragment extends Fragment implements View.OnClickListener {
    private TextView name;
    private TextView address;
    private String nameValue;
    private String addressValue;
    private ImageButton closeButton;
    private ImageButton editButton;
    private ImageButton deleteButton;

    /**
     * Construct for this class
     * @param name name of contact to display
     * @param address address of contact to display
     */
    public ContactFragment(String name, String address) {
        nameValue = name;
        addressValue = address;
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
        View view = inflater.inflate(R.layout.contact_popup, container, false);
        name = view.findViewById(R.id.nameContact);
        address = view.findViewById(R.id.addressContact);
        closeButton = view.findViewById(R.id.closeContactPopup);
        closeButton.setOnClickListener(this);
        editButton = view.findViewById(R.id.editButton);
        editButton.setOnClickListener(this);
        deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);
        name.setText(nameValue);
        address.setText(addressValue);

        return view;
    }

    /**
     * Setter for name of contact
     * @param name name of contact
     */
    public void setName(String name) {
        this.name.setText(name);
    }

    /**
     * Setter for address of contact
     * @param address address of contact
     */
    public void setAddress(String address) {
        this.address.setText(address);
    }

    /**
     * Handles all click events for fragment
     * @param v view of component that was clicked
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == closeButton.getId()) {
            this.getParentFragment().getChildFragmentManager().popBackStack();
        }
       else if(v.getId() == editButton.getId()) {
            ((AddressBookFragment)getParentFragment()).editContact(name.getText().toString(), address.getText().toString());
        }
       else if(v.getId() == deleteButton.getId()) {
            ((AddressBookFragment)getParentFragment()).deleteContact(name.getText().toString(), address.getText().toString());
        }
    }
}
