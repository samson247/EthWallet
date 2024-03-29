package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.capstonewallet.Views.Activities.WalletView;
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
    private ImageButton refreshButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        transactionListViewModel = new TransactionListViewModel(((WalletView)getActivity()).getWalletViewModel().getAddress());
        closeButton = view.findViewById(R.id.closeList);
        closeButton.setOnClickListener(this::onClick);
        refreshButton = view.findViewById(R.id.refreshList);
        refreshButton.setOnClickListener(this);

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
        transactionData = transactionListViewModel.getTransactionText();
        transactionText = transactionListViewModel.setTransactionText();
        adapter = new TransactionListAdapter(getContext(), this.transactionText);
        adapter.setFragmentManager(getParentFragment().getChildFragmentManager());
        adapter.setTransactionData(transactionListViewModel.getTransactionData());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING || !recyclerView.canScrollVertically(1))
                {
                    ((WalletView)getActivity()).hideNavBar();
                }
                else {
                    ((WalletView)getActivity()).showNavBar();
                }
            }
        });
    }

    public void refreshRecycler() throws Exception {
        transactionData = transactionListViewModel.getTransactionText();
        transactionText = transactionListViewModel.setTransactionText();
        adapter = null;
        adapter = new TransactionListAdapter(getContext(), this.transactionText);
        adapter.setFragmentManager(getParentFragment().getChildFragmentManager());
        adapter.setTransactionData(transactionListViewModel.getTransactionData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING || !recyclerView.canScrollVertically(1))
                {
                    ((WalletView)getActivity()).hideNavBar();
                }
                else {
                    ((WalletView)getActivity()).showNavBar();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == closeButton.getId()) {
            ((WalletView)getActivity()).showNavBar();
            this.getParentFragment().getChildFragmentManager().popBackStack();
            ((TransactionFragment) getParentFragment()).setHistoryVisible();
        }
        else {
            try {
                refreshRecycler();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}