package com.example.capstonewallet;

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

import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.LoginFragmentBinding;

public class TransactionFragment extends Fragment implements View.OnClickListener {
    FragmentTransactionBinding binding;
    TransactionModel transactionModel;
    //FragmentManager fragmentManager;
    //FragmentTransaction fragmentTransaction;
    WalletView walletView;
    /*
    public LoginFragment getInstance(Context context) {
        //super(R.layout.create_account);
        //walletModel = new WalletModel(context);
        return this;
    }*/

    public void setWalletView(WalletView walletView) {
        this.walletView = walletView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());
        transactionModel = new TransactionModel();
        transactionModel.connectToEthNetwork();
        //transactionModel.sendEther("7889");
        final ImageButton sendEtherButton = (ImageButton) view.findViewById(binding.imageButton.getId());
        sendEtherButton.setOnClickListener(this::onClick);

        Button button = (Button) view.findViewById(binding.button6.getId());
        button.setOnClickListener(this::onClick);

        EditText editText = (EditText) view.findViewById(binding.getEtherAmount.getId());

        final Button historyButton = (Button) view.findViewById(binding.button5.getId());
        historyButton.setOnClickListener(this::onClick);



        //fragmentManager = getSupportFragmentManager();
        //fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        //loginButton.setOnClickListener(this::onClick);
        //final Button createAccountButton = (Button) view.findViewById(fragmentBinding.createAccountButton.getId());
        //createAccountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "oncreateview Trans Frag");
        return view;
    }


    @Override
    public void onClick(View v) {
        Log.d("yo123", "List?");
        if(v.getId() == binding.imageButton.getId()) {
            transactionModel.sendEther(binding.editTextTextPersonName4.getText().toString());
        }
        else if(v.getId() == binding.button5.getId()) {
            walletView.addListFragment();

            Log.d("yo123", "onclicked");
        }
        else if(v.getId() == binding.button6.getId()) {
            walletView.startBraintree();
        }
    }
}