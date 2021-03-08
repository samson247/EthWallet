package com.example.capstonewallet.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.capstonewallet.R;
import com.example.capstonewallet.viewmodels.AccountViewModel;
import com.example.capstonewallet.viewmodels.CredentialsViewModel;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.viewmodels.WalletViewModel;

import java.util.ArrayList;

// Last button in navigation bar will load this fragment
public class AccountFragment extends Fragment {
    private TextView walletName;
    private TextView address;
    private TextView publicKey;
    private TextView privateKey;
    private TextView password;
    private ImageButton nameButton;
    private ImageButton addressButton;
    private ImageButton publicKeyButton;
    private ImageButton privateKeyButton;
    private ImageButton passwordButton;
    private AccountViewModel accountViewModel;

    /**
     * Creates the view for the AccountFragment
     * @param inflater builds layout from specified xml file
     * @param container parent container of the fragment
     * @param args bundle of arguments passed to fragment
     * @return the inflated view for the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);

        Log.d("yo123", "oncreateview AccountFragment");

        Switch showHideSwitch = view.findViewById(R.id.switch1);
        showHideSwitch.setOnCheckedChangeListener(this::onCheckChanged);

        nameButton = view.findViewById(R.id.nameButton);
        nameButton.setOnTouchListener(this::onTouch);

        addressButton = view.findViewById(R.id.addressButton);
        addressButton.setOnTouchListener(this::onTouch);

        publicKeyButton = view.findViewById(R.id.publicKeyButton);
        publicKeyButton.setOnTouchListener(this::onTouch);

        privateKeyButton = view.findViewById(R.id.privateKeyButton);
        privateKeyButton.setOnTouchListener(this::onTouch);

        passwordButton = view.findViewById(R.id.passwordButton);
        passwordButton.setOnTouchListener(this::onTouch);

        walletName = view.findViewById(R.id.walletNameTextView);
        address = view.findViewById(R.id.addressTextView);
        publicKey = view.findViewById(R.id.publicKeyTextView);
        privateKey = view.findViewById(R.id.privateKeyTextView);
        password = view.findViewById(R.id.passwordTextView);

        Bundle bundle = getArguments();

        if(bundle != null) {
            ArrayList<String> credentials = bundle.getStringArrayList("credentials");
            Log.d("yo123", "account bundle");
            accountViewModel = new AccountViewModel(credentials);
        }

        walletName.setText(accountViewModel.getWalletName());
        address.setText(accountViewModel.getAddress());
        privateKey.setText(accountViewModel.getPrivateKey());
        password.setText(accountViewModel.getPassword());

        return view;
    }

    /**
     *
     * @param view
     * @param motionEvent
     * @return
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TextView credential = null;

        if(view.getId() == nameButton.getId()) {
            credential = walletName;
        }
        else if(view.getId() == addressButton.getId()) {
            credential = address;
        }
        else if(view.getId() == publicKeyButton.getId()) {
            credential = publicKey;
        }
        else if(view.getId() == privateKeyButton.getId()) {
            credential = privateKey;
        }
        else if(view.getId() == passwordButton.getId()) {
            credential = password;
        }

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            credential.setTransformationMethod(null);
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            credential.setTransformationMethod(new PasswordTransformationMethod());
        }
        return false;
    }

    /**
     *
     * @param showHideSwitch
     * @param state
     */
    private void onCheckChanged(CompoundButton showHideSwitch, boolean state) {
        if (state == true) {
            //password.setInputType(InputType.TYPE_CLASS_TEXT);
            privateKey.setTransformationMethod(null);
            password.setTransformationMethod(null);
            publicKey.setTransformationMethod(null);
        } else if (state == false) {
            Log.d("yo123", "doing it");
            //password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            privateKey.setTransformationMethod(new PasswordTransformationMethod());
            password.setTransformationMethod(new PasswordTransformationMethod());
            publicKey.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    /**
     *
     */
    private class TouchButton extends AppCompatButton {
        public TouchButton(Context context) {
            super(context);
        }

        @Override
        public boolean performClick() {
            // do what you want
            return true;
        }
    }

    /**
     * Sets the value
     * @param password password for wallet account
     */
    public void setPassword(String password) {
        this.password.setText(password);
    }

    /**
     *
     * @param publicKey
     */
    public void setPublicKey(String publicKey) {
        this.publicKey.setText(publicKey);
    }
}
