package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstonewallet.R;
import com.example.capstonewallet.viewmodels.AccountViewModel;
import java.util.ArrayList;

/**
 * Class that displays credentials for account and account settings
 *
 * @author Sam Dodson
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
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
    private ImageButton settingsButton;
    private RelativeLayout topLayout;
    private ScrollView scrollView;
    private AccountViewModel accountViewModel;
    private Animation increaseWidth;

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

        privateKeyButton = view.findViewById(R.id.privateKeyButton);
        privateKeyButton.setOnClickListener(this);
        privateKeyButton2 = view.findViewById(R.id.privateKeyButton2);
        privateKeyButton2.setOnClickListener(this);
        passwordButton = view.findViewById(R.id.passwordButton);
        passwordButton.setOnClickListener(this);
        walletName = view.findViewById(R.id.walletNameTextView);
        addressText = view.findViewById(R.id.addressTextView);
        privateKeyHeading = view.findViewById(R.id.privateKeyHeading);
        privateKeyHeading.setOnClickListener(this);
        privateKeyHeading2 = view.findViewById(R.id.privateKeyHeading2);
        privateKeyHeading2.setOnClickListener(this);
        privateKeyText = view.findViewById(R.id.privateKeyTextView);
        privateKeyText2 = view.findViewById(R.id.privateKeyTextView2);
        passwordHeading = view.findViewById(R.id.passwordHeading);
        passwordHeading.setOnClickListener(this);
        passwordText = view.findViewById(R.id.passwordTextView);
        addressHeading = view.findViewById(R.id.addressHeading);
        addressHeading2 = view.findViewById(R.id.addressHeading2);
        addressText2 = view.findViewById(R.id.addressTextView2);
        settingsButton = view.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this);
        topLayout = view.findViewById(R.id.topLayout);
        scrollView = view.findViewById(R.id.scrollView);

        /*increaseWidth = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.increase_width);
        increaseWidth.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                privateKeyButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });*/

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

    /**
     * Handles all click events associated with this fragment and swaps appropriate headings to
     * display credentials
     * @param v view of this fragment
     */
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
        else if(v.getId() == settingsButton.getId()) {
            hideFragments();
            SettingsFragment fragment = new SettingsFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.settingsContainer, fragment, null);
            fragmentTransaction.addToBackStack("SettingsFragment");
            fragmentTransaction.commit();
        }
    }

    /**
     * Other fragments are hidden when settings fragment is shown
     */
    public void hideFragments() {
        topLayout.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
    }

    /**
     * Other fragments are shown when settings fragment is popped
     */
    public void showFragments() {
        topLayout.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
    }

    /**
     * Settings fragment is popped from back stack
     */
    public void popSettingsFragment() {
        showFragments();

        if(!getChildFragmentManager().isStateSaved()) {
            this.getChildFragmentManager().popBackStackImmediate();
        }
    }
}
