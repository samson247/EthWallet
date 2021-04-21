package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.viewmodels.TransactionViewModel;

public class DropdownFragment extends Fragment {
    private TextView balanceText1;
    private TextView balanceText2;
    private TextView usdValue;

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.balance_fragment, container, false);

        balanceText1 = view.findViewById(R.id.balanceText1);
        balanceText2 = view.findViewById(R.id.balanceText2);
        usdValue = view.findViewById(R.id.usdAmountText);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setBalanceText();
    }

    public void setBalanceText() {
        String balance = ((TransactionFragment)getParentFragment()).getBalance();
        Log.d("yo1234", "balancee: " + balance);
        if(balance.contains("ETH")) {
            String gweiBalance = ((TransactionFragment)getParentFragment()).getViewModel().convertBalance("Gwei");
            String weiBalance = ((TransactionFragment)getParentFragment()).getViewModel().convertBalance("Wei");
            balanceText1.setText(gweiBalance + " Gwei");
            balanceText2.setText(weiBalance + " Wei");
        }
        else if(balance.contains("Gwei")) {
            String ethBalance = ((TransactionFragment)getParentFragment()).getViewModel().getBalance();
            String weiBalance = ((TransactionFragment)getParentFragment()).getViewModel().convertBalance("Wei");
            balanceText1.setText(ethBalance + " ETH");
            balanceText2.setText(weiBalance + " Wei");
        }
        else {
            String ethBalance = ((TransactionFragment)getParentFragment()).getViewModel().getBalance();
            String gweiBalance = ((TransactionFragment)getParentFragment()).getViewModel().convertBalance("Gwei");
            balanceText1.setText(ethBalance + " ETH");
            balanceText2.setText(gweiBalance + " Gwei");
        }
        String price = ((WalletView)getActivity()).getWalletViewModel().getEtherPrice();
        Double result = Double.parseDouble(price) * Double.parseDouble(((TransactionFragment)getParentFragment()).getViewModel().getBalance());
        String roundedBalance = String.format("%.2f", result);
        usdValue.setText("$" + roundedBalance);
    }
}
