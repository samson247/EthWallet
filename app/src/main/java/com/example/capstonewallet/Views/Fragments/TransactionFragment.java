package com.example.capstonewallet.Views.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.viewmodels.TransactionViewModel;

/**
 * Fragment class for transaction, allows user to send and get ether
 *
 * @author Sam Dodson
 */
public class TransactionFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private FragmentTransactionBinding binding;
    private TransactionViewModel transactionViewModel;
    private RadioGroup sendOrGetRadio;
    private TransactionSendFragment sendFragment;
    private TransactionGetFragment getFragment;
    private TransactionListFragment listFragment;
    private FragmentContainerView sendOrGetContainer;
    private FragmentManager fragmentManager;
    private TextView transactionHeading;
    private String privateKey;
    private TextView balance;
    private TextView history;
    private ImageView dropdownButton;
    private DropdownFragment dropdownFragment;

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        if(this.getView() != null) {
            return this.getView();
        }
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ((WalletView)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());
        balance = view.findViewById(binding.balanceTextView.getId());
        history = view.findViewById(binding.transactionHistoryText.getId());
        history.setOnClickListener(this::onClick);
        transactionHeading = view.findViewById(binding.transactionHeading.getId());
        sendOrGetRadio = view.findViewById(R.id.sendOrGetRadio);
        sendOrGetRadio.setOnCheckedChangeListener(this::onCheckedChanged);
        sendOrGetContainer = view.findViewById(R.id.sendOrGetContainer);
        dropdownButton = view.findViewById(R.id.balanceDropdown);
        dropdownButton.setOnClickListener(this);

        // Gets private key for account that is passed in bundle
        Bundle bundle = getArguments();
        if(bundle != null) {
            privateKey = bundle.getString("privateKey");
        }
        Log.d("yo123", "in on create view trans");

        if(sendFragment == null) {
            addSendFragment();
        }
        ((WalletView)getActivity()).hideLoadingScreen();
        ((WalletView)getActivity()).showTopFragment();
        return view;
    }

    /**
     * Method called after view is created, asynchronously creates viewModel class and sets balance
     */
    @Override
    public void onStart() {
        super.onStart();
        if(transactionViewModel == null) {
            asyncInit();
        }
    }

    /**
     * Asynchronously creates view model class and sets balance
     */
    public void asyncInit() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                int gasLimit = sharedPreferences.getInt("gasLimit", 21000);
                int gasPrice = sharedPreferences.getInt("gasPrice", 20);
                transactionViewModel = new TransactionViewModel(privateKey, getContext(), gasLimit, gasPrice);
                balance.setText(transactionViewModel.getBalance());
            }
        });
        thread.run();
    }

    /**
     * Switches fragment and heading based on whether user is getting or sending ether
     * @param group radio button group
     * @param checkedId id of which button is checked
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group.indexOfChild(group.findViewById(checkedId)) == 0) {
            transactionHeading.setText("Send Ether");
            switchFragment(0);
        }
        else {
            transactionHeading.setText("Get Ether");
            switchFragment(1);
        }
    }

    /**
     * Handles click events
     * @param v view of clicked button
     */
    @Override
    public void onClick(View v) {
        // Adds list of transactions fragment
        if(v.getId() == history.getId()) {
            addListFragment();
        }
        else if(v.getId() == dropdownButton.getId()) {
            toggleDropdownFragment();
        }
    }

    /**
     * Getter for ether amount to be sent
     * @return
     */
    public String getEtherAmount() {
        return getFragment.getEtherAmount();
    }

    /**
     * Transaction list fragment is displayed
     */
    public void addListFragment() {
        Animation fadeOut = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.fade_out);
        history.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
                                         @Override
                                         public void onAnimationStart(Animation animation) { }

                                         @Override
                                         public void onAnimationEnd(Animation animation) {
                                            history.setVisibility(View.INVISIBLE);
                                         }

                                         @Override
                                         public void onAnimationRepeat(Animation animation) { }
                                     });

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        listFragment = new TransactionListFragment();
        fragmentTransaction.add(R.id.containerTransactionList, listFragment, null);
        fragmentTransaction.addToBackStack("ListFragment");
        fragmentTransaction.commit();
    }

    /**
     * Send fragment is displayed, this is done by default
     */
    public void addSendFragment() {
        Log.d("yo123", "adding send fragment");
        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TransactionSendFragment fragment = new TransactionSendFragment();
        sendFragment = fragment;
        fragmentTransaction.add(R.id.sendOrGetContainer, fragment, null);
        fragmentTransaction.commit();
    }

    /**
     * Fragments are switched between send and get, depending on the radio button that is selected
     * @param id the id of the selected button
     */
    public void switchFragment(int id) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        if(id == 0) {
            fragment = new TransactionSendFragment();
            if(fragmentManager.findFragmentById(R.id.sendOrGetContainer) != null) {
                fragmentManager.beginTransaction().remove(getFragment).commit();
            }
        }
        else if(id == 1){
            fragment = new TransactionGetFragment();
            getFragment = (TransactionGetFragment) fragment;
            if(fragmentManager.findFragmentById(R.id.container_top) != null) {
                fragmentManager.beginTransaction().remove(sendFragment).commit();
            }
        }
        fragmentManager.beginTransaction().replace(R.id.sendOrGetContainer, fragment).commit();
    }

    public void toggleDropdownFragment() {
        Log.d("yo123", "toggling dropdwn");
        if(dropdownFragment == null) {
            dropdownButton.setImageDrawable(getResources().getDrawable(R.drawable.drop_up, null));
            fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DropdownFragment fragment = new DropdownFragment();
            dropdownFragment = fragment;
            fragmentTransaction.add(R.id.dropdownContainer, fragment, null);
            fragmentTransaction.commit();
        }
        else if(dropdownFragment.isHidden()) {
            dropdownButton.setImageDrawable(getResources().getDrawable(R.drawable.drop_up, null));
            getChildFragmentManager().beginTransaction().show(dropdownFragment).commit();
            dropdownFragment.setBalanceText();
        }
        else {
            dropdownButton.setImageDrawable(getResources().getDrawable(R.drawable.drop_down_big, null));
            getChildFragmentManager().beginTransaction().hide(dropdownFragment).commit();
            //dropdownFragment = null;
        }
    }

    /**
     * Hides fragments in order to display transaction info
     */
    public void hideFragments() {
        Log.d("yo123", "hiding");
        sendOrGetRadio.setVisibility(View.INVISIBLE);
        transactionHeading.setVisibility(View.INVISIBLE);
        balance.setVisibility(View.INVISIBLE);
        sendOrGetContainer.setVisibility(View.INVISIBLE);
        getChildFragmentManager().beginTransaction().hide(listFragment).commit();
    }

    /**
     * Shows fragments after transaction info fragment has been popped
     */
    public void showFragments() {
        Log.d("yo123", "showing");
        sendOrGetRadio.setVisibility(View.VISIBLE);
        transactionHeading.setVisibility(View.VISIBLE);
        balance.setVisibility(View.VISIBLE);
        sendOrGetContainer.setVisibility(View.VISIBLE);
        getChildFragmentManager().beginTransaction().show(listFragment).commit();
    }

    /**
     * Pops transaction info fragment from back stack after back arrow is pushed
     */
    public void popTransactionInfoFragment() {
        showFragments();

        if(!getChildFragmentManager().isStateSaved()) {
            this.getChildFragmentManager().popBackStackImmediate();
        }
    }

    /**
     * Getter for view model
     * @return the instance of view model for transaction
     */
    public TransactionViewModel getViewModel() {
        return this.transactionViewModel;
    }

    /**
     * Makes transaction history fragment visible
     */
    public void setHistoryVisible() {
        history.setVisibility(View.VISIBLE);
    }

    /**
     * Setter for balance
     * @param newBalance the new balance value
     */
    public void setBalance(String newBalance) {
        if(newBalance.length() > 20) {
            balance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        }
        else {
            balance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        }
        balance.setText("Balance: " + newBalance);
    }

    public String getBalance() {
        return balance.getText().toString();
    }

    public DropdownFragment getDropdownFragment() {
        return dropdownFragment;
    }

    /**
     * Get fragment is notified of result of Braintree
     * @param result the code resulting from Braintree
     */
    public void notifyGet(int result) {
        getFragment.notifyResult(result);
    }

    /**
     * When this fragment is paused the send fragment becomes null
     */
    @Override
    public void onPause () {
        if(dropdownFragment != null) {
            getChildFragmentManager().beginTransaction().remove(dropdownFragment).commit();
            dropdownFragment = null;
        }
        getChildFragmentManager().popBackStack();
        super.onPause();
    }
}