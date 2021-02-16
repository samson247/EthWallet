package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.braintreepayments.api.dropin.DropInRequest;
import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.Models.TransactionModel;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.viewmodels.TransactionViewModel;
import com.example.capstonewallet.viewmodels.WalletViewModel;

public class TransactionFragment extends Fragment implements View.OnClickListener {
    FragmentTransactionBinding binding;
    TransactionViewModel transactionViewModel;
    //WalletView walletView;
    EditText editText;
    BraintreeClient client;

    public TransactionFragment(WalletView walletView) {
        //this.walletView = walletView;
    }

    public void setWalletView(WalletView walletView) {
        //this.walletView = walletView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());

        final ImageButton sendEtherButton = (ImageButton) view.findViewById(binding.imageButton.getId());
        sendEtherButton.setOnClickListener(this::onClick);

        Button button = (Button) view.findViewById(binding.button6.getId());
        button.setOnClickListener(this::onClick);

        editText = (EditText) view.findViewById(binding.getEtherAmount.getId());

        RelativeLayout layout = (RelativeLayout) view.findViewById((binding.transactionHistory.getId()));
        layout.setOnClickListener(this::onClick);

        Bundle bundle = getArguments();
        if(bundle != null) {
            String privateKey = bundle.getString("privateKey");
            transactionViewModel = new TransactionViewModel(privateKey);
            Log.d("yo123", "privateKeyFromTrans: " + privateKey);
        }

        Log.d("yo123", "oncreateview Trans Frag");
        return view;
    }


    @Override
    public void onClick(View v) {
        Log.d("yo123", "List?");
        if(v.getId() == binding.imageButton.getId()) {
            transactionViewModel.forwardSendEther(binding.editTextTextPersonName4.getText().toString(), "1");
            //transactionModel.sendEther(binding.editTextTextPersonName4.getText().toString(), "1");
        }
        else if(v.getId() == binding.transactionHistory.getId()) {
            //walletView.addListFragment();
            addListFragment();

            Log.d("yo123", "onclicked");
        }
        else if(v.getId() == binding.button6.getId()) {
            //FIXME get the amount to be received and send it to user upon successful payment
            //walletView.startBraintree();
            startBraintree();
        }
    }

    public String getEtherAmount() {
        Log.d("yo123", "binding" + binding.getEtherAmount.getText().toString());
        Log.d("yo123", "view" + editText.getText().toString());
        return editText.getText().toString();
        //return binding.getEtherAmount.getText().toString();
    }

    public void addListFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddContactFragment listFragment = new AddContactFragment(getContext());
        fragmentTransaction.add(R.id.containerTransactionList, listFragment, null);
        fragmentTransaction.commit();
    }

    public void startBraintree()  {
        DropInRequest dropInRequest = transactionViewModel.getDropInRequest();
        Log.d("yo123", "dropin created");
        startActivityForResult(dropInRequest.getIntent(this.getContext()), 400);
    }
}