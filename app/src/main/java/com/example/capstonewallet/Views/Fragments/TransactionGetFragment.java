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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!((TransactionFragment)getParentFragment()).getViewModel().getAppBalance()) {
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
            //set loading bar
            Log.d("yo123", "goint to braintree...");
            ((WalletView)getActivity()).startBraintree();
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
        String amountToSend = amount.getText().toString();
        amount.setText(null);
        Log.d("yo123", "result of braintree = " + result);
        if(result == 1) {
            toastOnUIThread("Transaction sent");
            // send ether to user of amount = what they paid
            ((TransactionFragment)getParentFragment()).getViewModel().forwardGetEther(amountToSend, unitOptions.getSelectedItem().toString(), this);
            //Toast.makeText(getContext(), "Transaction sent", Toast.LENGTH_LONG);
            //toastOnUIThread();
        }
        else {
            toastOnUIThread("Transaction Failed");
        }
    }

    /**
     * Getter for ether amount entered by user
     * @return the ether amount entered by user
     */
    public String getEtherAmount() {
        return amount.getText().toString();
    }

    /**
     * Notifies user when transaction has been mined
     */
    public void toastOnUIThread(String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
