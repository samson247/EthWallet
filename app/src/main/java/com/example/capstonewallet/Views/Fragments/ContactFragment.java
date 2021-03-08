package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Adapters.ContactsAdapter;
import com.example.capstonewallet.viewmodels.AddressBookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout layout;
    private TextView name;
    private TextView address;
    private String nameValue;
    private String addressValue;
    private ImageButton closeButton;

    public ContactFragment(String name, String address) {
        nameValue = name;
        addressValue = address;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.contact_popup, container, false);
        layout = view.findViewById(R.id.popupLayout);
        name = view.findViewById(R.id.nameContact);
        address = view.findViewById(R.id.addressContact);
        closeButton = view.findViewById(R.id.closeContactPopup);
        closeButton.setOnClickListener(this::onClick);
        name.setText(nameValue);
        address.setText(addressValue);

        return view;
    }
    public void setName(String name) {
        this.name.setText(name);
    }

    public void setAddress(String address) {
        this.address.setText(address);
    }

    @Override
    public void onClick(View v) {
        Log.d("yo123", "exit clicked");
        this.getParentFragment().getChildFragmentManager().popBackStack();
    }
}
