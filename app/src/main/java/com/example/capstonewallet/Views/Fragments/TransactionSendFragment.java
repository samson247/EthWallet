package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.capstonewallet.R;

/**
 * Fragment class for sending Ether
 */
public class TransactionSendFragment extends Fragment implements View.OnClickListener {
    private ImageButton sendEtherButton;
    private EditText recipient;
    private EditText amount;
    private Spinner unitOptions;

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.transaction_send_fragment, container, false);

        sendEtherButton = view.findViewById(R.id.sendEtherButton);
        sendEtherButton.setOnClickListener(this::onClick);
        recipient = view.findViewById(R.id.etherRecipientEditText);
        amount = view.findViewById(R.id.amountEditText);
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

        return view;
    }

    /**
     * When send ether button is clicked ether is transferred to specified address
     * @param v the view of the button that was clicked
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == sendEtherButton.getId()) {
            try {
                if (amount.getText().toString().length() == 0 && recipient.getText().toString().length() == 0) {
                    toastOnUIThread("Invalid recipient and amount");
                } else if (amount.getText().toString().length() == 0) {
                    toastOnUIThread("Invalid amount");
                } else if (recipient.getText().toString().length() == 0) {
                    toastOnUIThread("Invalid recipient");
                } else if (((TransactionFragment) getParentFragment()).getViewModel().getTxInProgress()) {
                    toastOnUIThread("Transaction already in progress");
                } else {
                    toastOnUIThread("Transaction Sent");
                    ((TransactionFragment) getParentFragment()).getViewModel()
                            .forwardSendEther(recipient.getText().toString(), amount.getText().toString(),
                                    unitOptions.getSelectedItem().toString(), this);
                    amount.setText("");
                    recipient.setText("");
                }
            }
            catch (Exception e) {}
        }
    }

    public void setAmountEditable() {
        amount.setText("");
        amount.setCursorVisible(true);
        amount.setFocusable(true);
    }

    /**
     * Alerts user when transaction has been mined
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
