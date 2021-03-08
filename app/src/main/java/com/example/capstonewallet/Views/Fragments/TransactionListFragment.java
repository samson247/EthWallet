package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.Models.TransactionModel;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Adapters.TransactionListAdapter;
import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.databinding.FragmentTransactionListBinding;
import com.example.capstonewallet.viewmodels.TransactionListViewModel;

import java.util.ArrayList;

public class TransactionListFragment extends Fragment implements View.OnClickListener{
    FragmentTransactionBinding binding;
    TransactionModel transactionModel;
    FragmentTransactionListBinding listBinding;
    RecyclerView recyclerView;
    TransactionListAdapter adapter;
    ArrayList<String[]> transactionText = new ArrayList<>();
    //TransactionClient.TransactionData[] transactionData;
    ArrayList<TransactionClient.TransactionData> transactionData;
    TransactionListViewModel transactionListViewModel;
    private ImageButton closeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        Log.d("yo123", "oncreateview");

        transactionListViewModel = new TransactionListViewModel();

        try {
            setupTransactionRecyclerView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add elements to recycler view
        return view;
    }

    private void setupTransactionRecyclerView(View view) throws Exception {
        closeButton = view.findViewById(R.id.closeList);
        closeButton.setOnClickListener(this::onClick);

        recyclerView = view.findViewById(R.id.transaction_list);
        transactionData = transactionListViewModel.getTransactionText();
        transactionText = transactionListViewModel.setTransactionText();
        adapter = new TransactionListAdapter(getContext(), this.transactionText);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View v) {
        this.getParentFragment().getChildFragmentManager().popBackStack();
        ((TransactionFragment) getParentFragment()).setHistoryVisible();
    }


    // Move to client
    /*public void getTransactionText() throws Exception {
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
        transactionText.add("Received 12 ether");

        TransactionClient client = new TransactionClient();
        transactionData = new ArrayList<>();

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
        Log.d("translength", String.valueOf(transactionData.size()));
        for(int i = 0; i < transactionData.size(); i++) {
            sender = "sender" + transactionData.get(i).getSender();
            Log.d("yo123", "sender" + sender);
            //FIXME pass address and determine if sent or received
            //if(transactionData.get(i).getSender() == )

            transactionText.add(sender);
        }
    }*/
}