package com.example.capstonewallet.Views.Fragments;

import android.content.res.ColorStateList;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
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

import devlight.io.library.ntb.NavigationTabBar;


public class BottomNavigationFragment extends Fragment implements View.OnClickListener {
    private FragmentBottomNavigationBinding binding;
    private FragmentManager fragmentManager;
    private WalletView walletView;
    private NavigationTabBar navigationTabBar;
    private RelativeLayout transactionButton;
    private RelativeLayout contactsButton;
    private RelativeLayout newsButton;
    private RelativeLayout accountButton;

    /*
    public LoginFragment getInstance(Context context) {
        //super(R.layout.create_account);
        //walletModel = new WalletModel(context);
        return this;
    }*/

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

        contactsButton = (RelativeLayout) view.findViewById(R.id.contactsButton);
        contactsButton.setOnClickListener(this::onClick);

        newsButton = (RelativeLayout) view.findViewById(R.id.newsButton);
        newsButton.setOnClickListener(this::onClick);

        accountButton = (RelativeLayout) view.findViewById(R.id.accountButton);
        accountButton.setOnClickListener(this::onClick);
        //fragmentBinding.createAccountButton.setOnClickListener(this::onClick);
        //fragmentBinding.loginButton.setOnClickListener(this::onClick);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //final Button loginButton = (Button) view.findViewById(fragmentBinding.loginButton.getId());
        //loginButton.setOnClickListener(this::onClick);
        /*final Button transactionButton = (Button) view.findViewById(binding.button2.getId());
        transactionButton.setOnClickListener(this::onClick);
        final Button stockNewsButton = (Button) view.findViewById(binding.button3.getId());
        stockNewsButton.setOnClickListener(this::onClick);
        final Button accountButton = (Button) view.findViewById(binding.button4.getId());
        accountButton.setOnClickListener(this::onClick);
        Log.d("yo123", "oncreateview Bottom Frag");*/



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
            //TransactionFragment fragment = new TransactionFragment();
            transactionButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
            fragmentClass = "TransactionFragment";
            //walletView.switchTopFragment("TransactionFragment");
        }
        else if(v.getId() == binding.contactsButton.getId()) {
            fragmentClass = "AddressBookFragment";
            contactsButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));
            //walletView.switchTopFragment("");
        }
        else if(v.getId() == binding.newsButton.getId()) {
            fragmentClass = "StockNewsFragment";
            newsButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));
        }
        else if(v.getId() == binding.accountButton.getId()) {
            //AccountFragment fragment = new AccountFragment();
            fragmentClass = "AccountFragment";
            accountButton.setBackgroundColor(getResources().getColor(R.color.navytint, null));
            //walletView.switchTopFragment("AccountFragment");
        }

        //getActivity().getSupportFragmentManager().findFragmentByTag("transaction").getChildFragmentManager().beginTransaction().commit();
        //Fragment fragment = getChildFragmentManager().findFragmentByTag("transaction");
        //getChildFragmentManager().beginTransaction().add(R.id.container_top, fragment).commit();

        if(fragmentClass.equals("TransactionFragment")) {
            //Fragment fragment = getChildFragmentManager().findFragmentByTag("transaction");
            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("transaction");
            if(fragment != null) {
                Log.d("fragclass", "skipping dat");
                //getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_top, fragment).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_top, fragment).commit();
                //fragman.beginTransaction().remove(fragman.findFragmentById(R.id.fraglog)).commit();
                //fragmentManager.beginTransaction().replace(walletBinding.containerTop.getId(), fragment).commit();
            }
        }
        else {
            Log.d("fragclass", fragmentClass);
            walletView.switchTopFragment(fragmentClass);
        }

        //walletView.switchTopFragment(fragmentClass);

        //walletView.switchTopFragment(fragmentClass);
        //getActivity().getSupportFragmentManager().

    }
}