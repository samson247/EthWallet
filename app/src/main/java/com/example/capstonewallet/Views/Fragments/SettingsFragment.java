package com.example.capstonewallet.Views.Fragments;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.capstonewallet.Models.AppSharedPreferences;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.LoginView;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.viewmodels.SettingsViewModel;

/**
 * Fragment class for settings
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    AppSharedPreferences appPreferences;
    SettingsViewModel settingsViewModel;
    private TextView logoutButton;
    private TextView addAccountButton;
    private EditText gasPrice;
    private EditText gasLimit;
    private Button saveButton;
    private TextView speedIndicator;

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.settings, container, false);
        ((WalletView)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(this);
        addAccountButton = view.findViewById(R.id.addAccount);
        addAccountButton.setOnClickListener(this);
        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        speedIndicator = view.findViewById(R.id.speedIndicator);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingsViewModel = new SettingsViewModel(sharedPreferences);
        gasPrice = view.findViewById(R.id.gasPriceEdit);
        gasLimit = view.findViewById(R.id.gasLimitEdit);
        Log.d("yo123", "Share price: " + sharedPreferences.getInt("gasPrice", 12345));
        Log.d("yo123", "Share limit: " + sharedPreferences.getInt("gasLimit", 12345));
        gasPrice.setText(String.valueOf(sharedPreferences.getInt("gasPrice", 12345)));
        gasLimit.setText(String.valueOf(sharedPreferences.getInt("gasLimit", 12345)));
        gasPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        updateSpeedColor();

        return view;
    }

    /*public SettingsFragment() {
        //sharedPreferences = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        //appPreferences = new AppSharedPreferences(sharedpreferences);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingsViewModel = new SettingsViewModel(sharedPreferences);
    }*/

    /**
     * Handles click events for this fragment
     * @param v the view that generated the click event
     * TODO add click animation to clickable views
     */
    @Override
    public void onClick(View v) {
        // Loads login activity and fragment
        if(v.getId() == logoutButton.getId()) {
            Intent intent = new Intent(getActivity(), LoginView.class);
            startActivity(intent);
        }
        else if(v.getId() == addAccountButton.getId()) {
            // Loads login activity and add account fragment
            Intent intent = new Intent(getActivity(), LoginView.class);
            intent.putExtra("addAccount", 1);
            startActivity(intent);
        }
        else if(v.getId() == saveButton.getId()) {
            // Saves changes to gas limit or gas price
            settingsViewModel.setGasLimit(gasLimit.getText().toString());
            settingsViewModel.setGasPrice(gasPrice.getText().toString());
            saveButton.setVisibility(View.INVISIBLE);
            updateSpeedColor();
        }
    }

    public void updateSpeedColor() {
        if(Integer.parseInt(gasPrice.getText().toString()) >= 150) {
            //speedIndicator.setBackgroundColor(getResources().getColor(R.color.green, null));
            speedIndicator.setBackgroundResource(R.drawable.border_3);
            speedIndicator.setText("Fast");
        }
        else if(Integer.parseInt(gasPrice.getText().toString()) >= 100) {
           //speedIndicator.setBackgroundColor(getResources().getColor(R.color.teal_700, null));
           speedIndicator.setText("Avg");
           speedIndicator.setBackgroundResource(R.drawable.border_7);
        }
        else {
            //speedIndicator.setBackgroundColor(getResources().getColor(R.color.bt_error_red, null));
            speedIndicator.setText("Slow");
            speedIndicator.setBackgroundResource(R.drawable.border_6);
        }
    }
}
