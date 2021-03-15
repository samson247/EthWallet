package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;

public class TransactionGetFragment extends Fragment implements View.OnClickListener {
    private EditText amount;
    private Button getEtherButton;
    private Spinner unitOptions;
    private int result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.transaction_get_fragment, container, false);

        getEtherButton = view.findViewById(R.id.getEtherButton);
        getEtherButton.setOnClickListener(this::onClick);

        amount = view.findViewById(R.id.amountEditTextGet);

        unitOptions = view.findViewById(R.id.units);
        unitOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == getEtherButton.getId()) {
            //set loading bar
            Log.d("yo123", "goint to braintree...");
            ((WalletView)getActivity()).startBraintree();
        }
    }

    public void notifyResult(int result) {
        String amountToSend = amount.getText().toString();
        amount.setText(null);
        Log.d("yo123", "result of braintree = " + result);
        if(result == 1) {
            // send ether to user of amount = what they paid
            ((TransactionFragment)getParentFragment()).getViewModel().forwardGetEther(amountToSend);
            Toast.makeText(getContext(), "Transaction sent", Toast.LENGTH_LONG);
        }
    }

    public String getEtherAmount() {
        return amount.getText().toString();
    }
}
