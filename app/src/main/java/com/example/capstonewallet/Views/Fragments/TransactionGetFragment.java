package com.example.capstonewallet.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;

/**
 * Class representing the get ether functionalities for the Transaction fragment
 */
public class TransactionGetFragment extends Fragment implements View.OnClickListener {
    private EditText amount;
    private Button getEtherButton;
    private Spinner unitOptions;
    private TextView lowFundsText;
    private TextView linkToBuy;
    private ProgressBar progressBar;

    /**
     * The method called to initialize view for this class
     * @param inflater the layout inflater for transaction_get_fragment.xml
     * @param container the parent view of TransactionGetFragment
     * @param args null bundle
     * @return the view for this fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.transaction_get_fragment, container, false);

        getEtherButton = view.findViewById(R.id.getEtherButton);
        getEtherButton.setOnClickListener(this::onClick);
        amount = view.findViewById(R.id.amountEditTextGet);
        lowFundsText = view.findViewById(R.id.lowFundsMSG);
        linkToBuy = view.findViewById(R.id.linkToBuy);
        linkToBuy.setOnClickListener(this);
        unitOptions = view.findViewById(R.id.units);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.unitOptions, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        unitOptions.setAdapter(adapter);
        unitOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String balance = ((TransactionFragment)getParentFragment()).getViewModel()
                        .convertBalance(unitOptions.getSelectedItem().toString()) + " " + unitOptions.getSelectedItem();

                ((TransactionFragment)getParentFragment()).setBalance(balance);
                DropdownFragment fragment = ((TransactionFragment)getParentFragment()).getDropdownFragment();
                if(fragment != null) {
                    fragment.setBalanceText();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        progressBar = view.findViewById(R.id.progressBar3);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!((TransactionFragment)getParentFragment()).getViewModel().compareAppBalance("0")) {
            amount.setVisibility(View.INVISIBLE);
            unitOptions.setVisibility(View.INVISIBLE);
            getEtherButton.setVisibility(View.INVISIBLE);
            lowFundsText.setVisibility(View.VISIBLE);
            linkToBuy.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Represents on click functionality of class
     * @param v the view for this class
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == getEtherButton.getId()) {
            getEtherButton.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            if(((TransactionFragment)getParentFragment()).getViewModel().getTxInProgress()) {
                toastOnUIThread("Transaction already in progress");
            }
            else if(amount.getText().toString().equals("")) {
                toastOnUIThread("Invalid amount");
            }
            else if(((TransactionFragment)getParentFragment()).getViewModel().compareAppBalance(amount.getText().toString())) {
                ((WalletView)getActivity()).startBraintree();
            }
            else {
                toastOnUIThread("Insufficient App Funds");
            }
        }
        else if(v.getId() == linkToBuy.getId()) {
            Intent newsLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ethereum.org/en/get-eth/"));
            getContext().startActivity(newsLinkIntent);
        }
    }

    /**
     * Notifies class of result of Braintree transaction
     * @param result the result code of Braintree transaction
     */
    public void notifyResult(int result) {
        progressBar.setVisibility(View.INVISIBLE);
        getEtherButton.setVisibility(View.VISIBLE);
        String amountToSend = amount.getText().toString();
        amount.setText(null);
        if(result == 1) {
            toastOnUIThread("Transaction sent");
            // send ether to user of amount = what they paid
            ((TransactionFragment)getParentFragment()).getViewModel().forwardGetEther(amountToSend, unitOptions.getSelectedItem().toString(), this);
        }
        else {
            toastOnUIThread("Transaction Failed");
        }
        amount.setText("");
    }

    /**
     * Getter for ether amount entered by user
     * @return the ether amount entered by user
     */
    public String getEtherAmount() {
        return amount.getText().toString();
    }

    public void setAmountEditable() {
        amount.setText("");
        amount.setCursorVisible(true);
        amount.setFocusable(true);
    }

    /**
     * Notifies user when transaction has been mined
     */
    public void toastOnUIThread(String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
