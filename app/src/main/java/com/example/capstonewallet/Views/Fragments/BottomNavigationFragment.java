package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.databinding.FragmentBottomNavigationBinding;


public class BottomNavigationFragment extends Fragment implements View.OnClickListener {
    private FragmentBottomNavigationBinding binding;
    private FragmentManager fragmentManager;
    private WalletView walletView;
    private RelativeLayout transactionButton;
    private RelativeLayout contactsButton;
    private RelativeLayout newsButton;
    private RelativeLayout accountButton;
    private boolean transactionOpen = false;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setWalletView(WalletView walletView) {
        this.walletView = walletView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
        binding = FragmentBottomNavigationBinding.inflate(getLayoutInflater());

        transactionButton = (RelativeLayout) view.findViewById(R.id.transactionButton);
        transactionButton.setOnClickListener(this::onClick);
        transactionButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));

        contactsButton = (RelativeLayout) view.findViewById(R.id.contactsButton);
        contactsButton.setOnClickListener(this::onClick);

        newsButton = (RelativeLayout) view.findViewById(R.id.newsButton);
        newsButton.setOnClickListener(this::onClick);

        accountButton = (RelativeLayout) view.findViewById(R.id.accountButton);
        accountButton.setOnClickListener(this::onClick);
        return view;
    }


    @Override
    public void onClick(View v) {
        Log.d("yo123", "inonclick");

        transactionButton.setBackgroundColor(getResources().getColor(R.color.navy, null));
        contactsButton.setBackgroundColor(getResources().getColor(R.color.navy, null));
        newsButton.setBackgroundColor(getResources().getColor(R.color.navy, null));
        accountButton.setBackgroundColor(getResources().getColor(R.color.navy, null));

        String fragmentClass = "";

        if(v.getId() == binding.transactionButton.getId()) {
            if(!transactionOpen) {
                ((WalletView)getActivity()).hideTopFragment();
                ((WalletView)getActivity()).showLoadingScreen();
                transactionButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));
                transactionOpen = true;
                fragmentClass = "TransactionFragment";
            }
            else {
                transactionButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));
                return;
            }
        }
        else if(v.getId() == binding.contactsButton.getId()) {
            fragmentClass = "AddressBookFragment";
            contactsButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));
        }
        else if(v.getId() == binding.newsButton.getId()) {
            ((WalletView)getActivity()).hideTopFragment();
            ((WalletView)getActivity()).showLoadingScreen();
            fragmentClass = "StockNewsFragment";
            newsButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));
        }
        else if(v.getId() == binding.accountButton.getId()) {
            fragmentClass = "AccountFragment";
            accountButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));
        }

        if(!fragmentClass.equals("TransactionFragment")) {
            transactionOpen = false;
        }
        Log.d("fragclass", fragmentClass);
        walletView.switchTopFragment(fragmentClass);
    }
}