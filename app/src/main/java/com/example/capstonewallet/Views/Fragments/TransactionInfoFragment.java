package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;

import java.util.Date;

/**
 * Fragment class for transaction info popup
 */
public class TransactionInfoFragment extends Fragment {
    private TransactionClient.TransactionData transactionData;
    private TextView time;
    private TextView hash;
    private TextView sender;
    private TextView receiver;
    private TextView amount;
    private TextView gasPrice;
    private TextView gasLimit;
    private TextView gasUsed;

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.transaction_info_fragment, container, false);
        ((WalletView)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((TransactionFragment)getParentFragment()).hideFragments();

        time = view.findViewById(R.id.timeText);
        hash = view.findViewById(R.id.hashText);
        sender = view.findViewById(R.id.senderText);
        receiver = view.findViewById(R.id.receiverText);
        amount = view.findViewById(R.id.amountText);
        gasPrice = view.findViewById(R.id.gasPriceText);
        gasLimit = view.findViewById(R.id.gasLimitText);
        gasUsed = view.findViewById(R.id.gasUsedText);
        Date date = new java.util.Date(Long.parseLong(transactionData.getTime()) * 1000);
        time.setText("Time:           " + (String) DateFormat.format("yyyy-MM-dd HH:mm:ss", date));
        hash.setText(transactionData.getHash());
        sender.setText(transactionData.getSender());
        receiver.setText(transactionData.getReceiver());
        amount.setText("Amount:      " + transactionData.getValue());
        gasPrice.setText("Gas Price:   " + transactionData.getGasPrice());
        gasLimit.setText("Gas Limit:   " + transactionData.getGas());
        gasUsed.setText("Gas Used:   " + transactionData.getGasUsed());

        return view;
    }

    /**
     * Constructor for this class
     * @param transactionData the data corresponding to a transaction
     */
    public TransactionInfoFragment(TransactionClient.TransactionData transactionData) {
        this.transactionData = transactionData;
    }
}
