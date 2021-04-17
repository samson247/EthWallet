package com.example.capstonewallet.Views.Fragments;

import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.capstonewallet.Views.Activities.LoginView;
import com.example.capstonewallet.viewmodels.LoginViewModel;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.databinding.LoginFragmentBinding;


/**
 * Fragment class that allows user to login
 *
 * @author Sam Dodson
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private LoginViewModel loginViewModel;
    private LoginFragmentBinding fragmentBinding;
    private FragmentManager fragmentManager;
    private EditText password;
    private EditText address;
    private ProgressBar progressBar;
    private Button loginButton;

    /**
     * Initializes fragment manager of class
     * @param fragmentManager the WalletView activity fragment manager
     */
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        fragmentBinding = LoginFragmentBinding.inflate(getLayoutInflater());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginButton = view.findViewById(fragmentBinding.loginButton.getId());
        loginButton.setOnClickListener(this::onClick);

        final TextView createAccountTextView = (TextView) view.findViewById(fragmentBinding.createOrAddTextView.getId());
        createAccountTextView.setOnClickListener(this::onClick);

        progressBar = view.findViewById(fragmentBinding.progressBar.getId());

        password = (EditText) view.findViewById(R.id.editTextPassword);
        address = (EditText) view.findViewById(R.id.editTextWalletName);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            ((LoginView)getActivity()).switchFragments();
        }

        return view;
    }

    /**
     * Responds to click of login button, either starting next activity with account loaded
     * or informing user of invalid login credentials
     * @param v the view of the fragment
     */
    @Override
    public void onClick(View v) {
        Log.d("yo123", "inonclick");
        if(v.getId() == fragmentBinding.loginButton.getId()) {
            loginViewModel.setAddress(address.getText().toString());
            loginViewModel.setPassword(password.getText().toString());
            boolean proceed = loginViewModel.onClick(getContext());

            if(proceed) {
                loginButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                Intent intent = new Intent(getActivity(), WalletView.class);
                // pass credentials to next activity
                intent.putExtra("credentials", new String[]{loginViewModel.getPassword(), loginViewModel.getFileName()});
                startActivity(intent);
            }
            else {
                password.setText("");
                password.setHint("Invalid Password");
                address.setHintTextColor(Color.RED);
                password.setHintTextColor(Color.RED);
            }
        }
        else if (v.getId() == fragmentBinding.createOrAddTextView.getId()){
            ((LoginView)getActivity()).switchFragments();
        }
    }
}