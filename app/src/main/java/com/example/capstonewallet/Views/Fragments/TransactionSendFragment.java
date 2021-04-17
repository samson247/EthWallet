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

                //TODO convert balance to correct unit
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

    /**
     * When send ether button is clicked ether is transferred to specified address
     * @param v the view of the button that was clicked
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == sendEtherButton.getId()) {
            toastSent();
            ((TransactionFragment)getParentFragment()).getViewModel()
                    .forwardSendEther(recipient.getText().toString(), amount.getText().toString(),
                            unitOptions.getSelectedItem().toString(),this);
            amount.setText("");
            amount.setCursorVisible(false);
            amount.setFocusable(false);
        }
    }

    /**
     * Alerts user when transaction has been mined
     */
    public void toastOnUIThread() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Transaction Successful", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void toastSent() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Transaction Sent", Toast.LENGTH_LONG).show();
            }
        });
    }
}
