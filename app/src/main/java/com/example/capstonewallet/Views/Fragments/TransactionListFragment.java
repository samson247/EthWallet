package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.Models.TransactionModel;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Adapters.TransactionListAdapter;
import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.databinding.FragmentTransactionListBinding;

import java.util.ArrayList;

public class TransactionListFragment extends Fragment {
    FragmentTransactionBinding binding;
    TransactionModel transactionModel;
    FragmentTransactionListBinding listBinding;
    RecyclerView recyclerView;
    TransactionListAdapter adapter;
    ArrayList<String> transactionText = new ArrayList<>();
    TransactionClient.TransactionData[] transactionData;
    /*
    public LoginFragment getInstance(Context context) {
        //super(R.layout.create_account);
        //walletModel = new WalletModel(context);
        return this;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        Log.d("yo123", "oncreateview");

        try {
            setupTransactionRecyclerView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add elements to recycler view
        return view;
    }

    private void setupTransactionRecyclerView(View view) throws Exception {
        recyclerView = view.findViewById(R.id.transaction_list);

        getTransactionText();
        setTransactionText();
        adapter = new TransactionListAdapter(getContext(), this.transactionText);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // Move to client
    public void getTransactionText() throws Exception {
        /*transactionText.add("Sent 5 ether");
        transactionText.add("Received 5 ether");
        transactionText.add("Sent 7 ether");
        transactionText.add("Sent 20 ether");
        transactionText.add("Received 12 ether");
        transactionText.add("Sent 5 ether");
        transactionText.add("Received 5 ether");
        transactionText.add("Sent 7 ether");
        transactionText.add("Sent 20 ether");
        transactionText.add("Received 12 ether");
        transactionText.add("Sent 5 ether");
        transactionText.add("Received 5 ether");
        transactionText.add("Sent 7 ether");
        transactionText.add("Sent 20 ether");
        transactionText.add("Received 12 ether");*/

        TransactionClient client = new TransactionClient();
        transactionData = new TransactionClient.TransactionData[20];

        Thread thread = new Thread()
        {
            public void run() {
                try {
                    transactionData = client.getTransactions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();
    }

    public void setTransactionText() {
        String sender;
        for(int i = 0; i < 19; i++) {
            sender = "sender" + transactionData[i].getSender();
            Log.d("yo123", "sender" + sender);
            transactionText.add(sender);
        }
    }
}