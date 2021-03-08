package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.capstonewallet.R;
import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.viewmodels.TransactionViewModel;

public class TransactionSendFragment extends Fragment implements View.OnClickListener {
    private ImageButton sendEtherButton;
    private EditText recipient;
    private EditText amount;
    private Spinner unitOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.transaction_send_fragment, container, false);

        sendEtherButton = view.findViewById(R.id.sendEtherButton);
        sendEtherButton.setOnClickListener(this::onClick);

        recipient = view.findViewById(R.id.etherRecipientEditText);

        amount = view.findViewById(R.id.amountEditText);

        unitOptions = view.findViewById(R.id.units);
        unitOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // handle unit conversions etc.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == sendEtherButton.getId()) {
            ((TransactionFragment)getParentFragment()).getViewModel()
                    .forwardSendEther(recipient.getText().toString(), amount.getText().toString());
            //transactionModel.sendEther(binding.editTextTextPersonName4.getText().toString(), "1");
        }
    }
}
