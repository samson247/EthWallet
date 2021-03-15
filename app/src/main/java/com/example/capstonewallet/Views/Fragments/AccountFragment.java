package com.example.capstonewallet.Views.Fragments;

import android.content.Context;
import android.graphics.Color;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.capstonewallet.R;
import com.example.capstonewallet.viewmodels.AccountViewModel;
import com.example.capstonewallet.viewmodels.CredentialsViewModel;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.viewmodels.WalletViewModel;

import java.util.ArrayList;

// Last button in navigation bar will load this fragment
public class AccountFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private TextView walletName;
    private TextView addressHeading;
    private TextView addressText;
    private TextView addressHeading2;
    private TextView addressText2;
    private TextView passwordButton;
    private TextView privateKeyButton;
    private TextView privateKeyHeading;
    private TextView privateKeyButton2;
    private TextView privateKeyHeading2;
    private TextView privateKeyText;
    private TextView privateKeyText2;
    private TextView passwordHeading;
    private TextView passwordText;
    private ConstraintLayout constraintLayout;
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

        //Switch showHideSwitch = view.findViewById(R.id.switch1);
        //showHideSwitch.setOnCheckedChangeListener(this::onCheckChanged);

        privateKeyButton = view.findViewById(R.id.privateKeyButton);
        privateKeyButton.setOnClickListener(this::onClick);
        privateKeyButton2 = view.findViewById(R.id.privateKeyButton2);
        privateKeyButton2.setOnClickListener(this::onClick);

        passwordButton = view.findViewById(R.id.passwordButton);
        passwordButton.setOnClickListener(this::onClick);

        walletName = view.findViewById(R.id.walletNameTextView);
        addressText = view.findViewById(R.id.addressTextView);
        privateKeyHeading = view.findViewById(R.id.privateKeyHeading);
        privateKeyHeading.setOnClickListener(this::onClick);
        privateKeyHeading2 = view.findViewById(R.id.privateKeyHeading2);
        privateKeyHeading2.setOnClickListener(this::onClick);
        privateKeyText = view.findViewById(R.id.privateKeyTextView);
        privateKeyText2 = view.findViewById(R.id.privateKeyTextView2);

        passwordHeading = view.findViewById(R.id.passwordHeading);
        passwordHeading.setOnTouchListener(this::onTouch);
        passwordHeading.setOnClickListener(this::onClick);
        passwordText = view.findViewById(R.id.passwordTextView);

        addressHeading = view.findViewById(R.id.addressHeading);
        addressHeading2 = view.findViewById(R.id.addressHeading2);
        addressText2 = view.findViewById(R.id.addressTextView2);


        constraintLayout = view.findViewById(R.id.constraintLayout);

        Bundle bundle = getArguments();

        if(bundle != null) {
            ArrayList<String> credentials = bundle.getStringArrayList("credentials");
            Log.d("yo123", "account bundle");
            accountViewModel = new AccountViewModel(credentials);
        }

        walletName.setText(accountViewModel.getWalletName());
        addressText.setText(accountViewModel.getAddress());
        addressText2.setText(accountViewModel.getAddress());

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == privateKeyButton.getId()) {
            privateKeyButton.setVisibility(View.INVISIBLE);
            privateKeyHeading.setVisibility(View.VISIBLE);
            privateKeyText.setVisibility(View.VISIBLE);
            privateKeyText.setText(accountViewModel.getPrivateKey());
        }
        else if(v.getId() == privateKeyButton2.getId()) {
            privateKeyButton2.setVisibility(View.INVISIBLE);
            privateKeyHeading2.setVisibility(View.VISIBLE);
            privateKeyText2.setVisibility(View.VISIBLE);
            privateKeyText.setText(accountViewModel.getPrivateKey());
        }
        else if(v.getId() == privateKeyHeading.getId()) {
            privateKeyHeading.setVisibility(View.INVISIBLE);
            privateKeyText.setVisibility(View.INVISIBLE);
            privateKeyButton.setVisibility(View.VISIBLE);
        }
        else if(v.getId() == privateKeyHeading2.getId()) {
            privateKeyHeading2.setVisibility(View.INVISIBLE);
            privateKeyText2.setVisibility(View.INVISIBLE);
            privateKeyButton2.setVisibility(View.VISIBLE);
        }
        else if(v.getId() == passwordButton.getId()) {
            /*ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(passwordText.getId(), ConstraintSet.BOTTOM, address.getId(), ConstraintSet.TOP,20);
            constraintSet.applyTo(constraintLayout);*/
            if(privateKeyButton.getVisibility() == View.VISIBLE) {
                privateKeyButton.setVisibility(View.INVISIBLE);
                privateKeyButton2.setVisibility(View.VISIBLE);
            }
            else if(privateKeyHeading.getVisibility() == View.VISIBLE) {
                privateKeyHeading.setVisibility(View.INVISIBLE);
                privateKeyHeading2.setVisibility(View.VISIBLE);
                privateKeyText.setVisibility(View.INVISIBLE);
                privateKeyText2.setVisibility(View.VISIBLE);
            }
            addressText.setVisibility(View.INVISIBLE);
            addressHeading.setVisibility(View.INVISIBLE);
            addressText2.setVisibility(View.VISIBLE);
            addressHeading2.setVisibility(View.VISIBLE);
            passwordButton.setVisibility(View.INVISIBLE);
            passwordHeading.setVisibility(View.VISIBLE);
            passwordText.setVisibility(View.VISIBLE);
            passwordText.setText(accountViewModel.getPassword());
        }
        else if(v.getId() == passwordHeading.getId()) {
            if(privateKeyButton.getVisibility() == View.INVISIBLE && privateKeyButton2.getVisibility() == View.VISIBLE) {
                privateKeyButton2.setVisibility(View.INVISIBLE);
                privateKeyButton.setVisibility(View.VISIBLE);
            }
            else if(privateKeyHeading.getVisibility() == View.INVISIBLE && privateKeyHeading2.getVisibility() == View.VISIBLE) {
                privateKeyHeading2.setVisibility(View.INVISIBLE);
                privateKeyHeading.setVisibility(View.VISIBLE);
                privateKeyText2.setVisibility(View.INVISIBLE);
                privateKeyText.setVisibility(View.VISIBLE);
            }
            addressText2.setVisibility(View.INVISIBLE);
            addressHeading2.setVisibility(View.INVISIBLE);
            addressText.setVisibility(View.VISIBLE);
            addressHeading.setVisibility(View.VISIBLE);
            passwordHeading.setVisibility(View.INVISIBLE);
            passwordText.setVisibility(View.INVISIBLE);
            passwordButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == privateKeyButton.getId()) {
            if(event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                privateKeyButton.setBackgroundColor(Color.BLUE);
            }
        }
        return false;
    }

    /**
     *
     * @param view
     * @param motionEvent
     * @return
     */
    /*
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
    }*/


    /**
     *
     * @param showHideSwitch
     * @param state
     */
    /*
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
    }*/



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
}
