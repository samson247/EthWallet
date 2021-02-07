package com.example.capstonewallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.capstonewallet.databinding.FragmentBottomNavigationBinding;
import com.example.capstonewallet.databinding.LoginBinding;
import com.example.capstonewallet.databinding.LoginFragmentBinding;

import java.util.ArrayList;

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
        Log.d("yo123", "oncreateview Bottom Frag");

        navigationTabBar = (NavigationTabBar) view.findViewById(R.id.bottom_navigation_bar);


        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.account, null), navigationTabBar.getBgColor())
                        .title("account").build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eighth, null), navigationTabBar.getBgColor())
                        .title("Heart2").build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.getModels().get(0);
        navigationTabBar.setActiveColor(Color.WHITE);
        navigationTabBar.setSelected(true);

        //navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);

        //ntbSample1.setOnClickListener(this::onClick);
        //ntbSample1.getModels().indexOf(models1);
        //ntbSample1.setOnTabBarSelectedIndexListener(this::onTabBarSelected);
        NavigationTabBar.OnTabBarSelectedIndexListener listener = new NavigationTabBar.OnTabBarSelectedIndexListener() {
            String fragmentClass = "";
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                //model.getBadgeTitle() == ntbSample1.getModelIndex();
                //Log.d("yo123", model.getBadgeTitle());
                // Log.d("yo123", String.valueOf(ntbSample1.getModels().indexOf(model)));
                // Log.d("yo123", String.valueOf(index));
                Log.d("yo123", "here" + navigationTabBar.getModels().get(index).getTitle());

                if(navigationTabBar.getModels().get(index).getTitle() == "account") {
                    Log.d("yo123", "account");
                    navigationTabBar.getModels();
                    fragmentClass = "AccountFragment";
                }
                walletView.switchTopFragment(fragmentClass);
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        };

        navigationTabBar.setOnTabBarSelectedIndexListener(listener);
        listener.onStartTabSelected(navigationTabBar.getModels().get(0), 0);
        navigationTabBar.setSelected(true);
        /*navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            String fragmentClass = "";
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
                //model.getBadgeTitle() == ntbSample1.getModelIndex();
                //Log.d("yo123", model.getBadgeTitle());
               // Log.d("yo123", String.valueOf(ntbSample1.getModels().indexOf(model)));
               // Log.d("yo123", String.valueOf(index));
                Log.d("yo123", "here" + navigationTabBar.getModels().get(index).getTitle());

                if(navigationTabBar.getModels().get(index).getTitle() == "account") {
                    Log.d("yo123", "account");
                    fragmentClass = "AccountFragment";
                }
                walletView.switchTopFragment(fragmentClass);
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                //model.hideBadge();
            }
        });*/

        //navigationTabBar.setIsBadged(true);
        //navigationTabBar.getModels().get(0).hideBadge();
        //navigationTabBar.setSelected(true);*/

        return view;
    }


    @Override
    public void onClick(View v) {
        Log.d("yo123", "inonclick");
        String fragmentClass = "";
        if(v.getId() == binding.transactionButton.getId()) {
            //TransactionFragment fragment = new TransactionFragment();
            fragmentClass = "TransactionFragment";
            //walletView.switchTopFragment("TransactionFragment");
        }
        else if(v.getId() == binding.contactsButton.getId()) {
            fragmentClass = "AddressBookFragment";
            //walletView.switchTopFragment("");
        }
        else if(v.getId() == binding.newsButton.getId()) {
            fragmentClass = "StockNewsFragment";
        }
        else if(v.getId() == binding.accountButton.getId()) {
            //AccountFragment fragment = new AccountFragment();
            fragmentClass = "AccountFragment";
            //walletView.switchTopFragment("AccountFragment");
        }

        walletView.switchTopFragment(fragmentClass);

    }

    public void onTabBarSelected(View v) {

    }
}