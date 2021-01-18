package com.example.capstonewallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.databinding.FragmentTransactionListBinding;

public class TransactionListFragment extends Fragment {
    FragmentTransactionBinding binding;
    TransactionModel transactionModel;
    FragmentTransactionListBinding listBinding;
    RecyclerView recyclerView;
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
        recyclerView = (RecyclerView) view.findViewById(listBinding.recycler.getId());
        // Add elements to recycler view
        return view;
    }
}