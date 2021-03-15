package com.example.capstonewallet.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.braintreepayments.api.dropin.DropInRequest;
import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Models.Clients.BraintreeClient;
import com.example.capstonewallet.Models.TransactionModel;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.databinding.FragmentTransactionBinding;
import com.example.capstonewallet.viewmodels.TransactionViewModel;
import com.example.capstonewallet.viewmodels.WalletViewModel;

public class TransactionFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    FragmentTransactionBinding binding;
    TransactionViewModel transactionViewModel;
    WalletView walletView;
    EditText editText;
    BraintreeClient client;
    RadioGroup sendOrGetRadio;
    TransactionSendFragment sendFragment;
    TransactionGetFragment getFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView transactionHeading;
    ImageButton sendEtherButton;
    private String privateKey;
    private TextView balance;
    private TextView history;

    public TransactionFragment(WalletView walletView) {
    }

    public void setWalletView(WalletView walletView) {
        this.walletView = walletView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        if(this.getView() != null) {
            Log.d("yo123", "view already created");
            return this.getView();
        }
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());

        //sendEtherButton = (ImageButton) view.findViewById(binding.sendEtherButton.getId());
        //sendEtherButton.setOnClickListener(this::onClick);

        //Button button = (Button) view.findViewById(binding.button6.getId());
        //button.setOnClickListener(this::onClick);

        //editText = (EditText) view.findViewById(binding.getEtherAmount.getId());

        //RelativeLayout layout = (RelativeLayout) view.findViewById((binding.transactionHistory.getId()));
        //layout.setOnClickListener(this::onClick);

        balance = view.findViewById(binding.balanceTextView.getId());
        history = view.findViewById(binding.transactionHistoryText.getId());
        history.setOnClickListener(this::onClick);
        transactionHeading = view.findViewById(binding.transactionHeading.getId());

        sendOrGetRadio = view.findViewById(R.id.sendOrGetRadio);
        sendOrGetRadio.setOnCheckedChangeListener(this::onCheckedChanged);

        Bundle bundle = getArguments();
        if(bundle != null) {
            privateKey = bundle.getString("privateKey");
            //transactionViewModel = new TransactionViewModel(privateKey);
            //textView.setText(transactionViewModel.getBalance());
            //transactionViewModel = new TransactionViewModel(privateKey);
            Log.d("yo123", "privateKeyFromTrans: " + privateKey);
        }

        //textView.setText(transactionViewModel.getBalance());
        //fragmentManager = getActivity().getSupportFragmentManager();
        addSendFragment();

        Log.d("yo123", "oncreateview Trans Frag");
        return view;
    }

    @Override
    public void onStart() {
        Log.d("on start", "In onstart");
        super.onStart();
        if(transactionViewModel == null) {
            asyncInit();
        }
        balance.setText(transactionViewModel.getBalance());
        //asyncInit();
        //transactionViewModel = new TransactionViewModel(privateKey);
        //textView.setText(transactionViewModel.getBalance());
    }

    public void asyncInit() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                transactionViewModel = new TransactionViewModel(privateKey);
                balance.setText(transactionViewModel.getBalance());
            }
        });
        thread.run();
    }

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


    @Override
    public void onClick(View v) {
        Log.d("yo123", "List?");
        /*if(v.getId() == sendEtherButton.getId()) {
            transactionViewModel.forwardSendEther(binding.etherRecipientEditText.getText().toString(), "1");
            //transactionModel.sendEther(binding.editTextTextPersonName4.getText().toString(), "1");
        }*/
        if(v.getId() == history.getId()) {
            //walletView.addListFragment();
            addListFragment();

            Log.d("yo123", "onclicked");
        }
        /*else if(v.getId() == binding.button6.getId()) {
            //FIXME get the amount to be received and send it to user upon successful payment
            ((WalletView)getActivity()).startBraintree();
            //startBraintree();
        }*/
    }

    public String getEtherAmount() {
        //Log.d("yo123", "binding" + binding.getEtherAmount.getText().toString());
        //Log.d("yo123", "view" + editText.getText().toString());
        return getFragment.getEtherAmount();
        //return editText.getText().toString();
        //return binding.getEtherAmount.getText().toString();
    }

    public void addListFragment() {
        Animation fadeOut = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.fade_out);
        history.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
                                         @Override
                                         public void onAnimationStart(Animation animation) {

                                         }

                                         @Override
                                         public void onAnimationEnd(Animation animation) {
                                            history.setVisibility(View.INVISIBLE);
                                         }

                                         @Override
                                         public void onAnimationRepeat(Animation animation) {

                                         }
                                     });

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TransactionListFragment listFragment = new TransactionListFragment();
        fragmentTransaction.add(R.id.containerTransactionList, listFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void addSendFragment() {
        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TransactionSendFragment fragment = new TransactionSendFragment();
        sendFragment = fragment;
        fragmentTransaction.add(R.id.sendOrGetContainer, fragment, null);
        fragmentTransaction.commit();
    }

    /*public void addGetFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TransactionGetFragment fragment = new TransactionGetFragment();
        fragmentTransaction.add(R.id.sendOrGetContainer, fragment, null);
        fragmentTransaction.commit();

        if(fragmentManager.findFragmentById(R.id.sendOrGetContainer) != null) {
            fragmentManager.beginTransaction().remove(sendFragment).commit();
        }
        fragmentManager.beginTransaction().replace(R.id.sendOrGetContainer, fragment).commit();
    }*/

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

    public void startBraintree()  {
        DropInRequest dropInRequest = transactionViewModel.getDropInRequest();
        Log.d("yo123", "dropin created");
        startActivityForResult(dropInRequest.getIntent(this.getContext()), 400);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, String amount) {
        Log.d("onactres", "here wtrasfrag");
        Log.d("onactres", "request " + requestCode);
        Log.d("onactres", "result " + resultCode);
        Log.d("onactres", "amount " + amount);
        transactionViewModel.onActivityResult(requestCode, resultCode, data, amount);
    }

    public TransactionViewModel getViewModel() {
        return this.transactionViewModel;
    }

    public void setHistoryVisible() {
        history.setVisibility(View.VISIBLE);
    }

    public String convertUnit(String oldUnit, String newUnit, String amount) {
        String value = null;

        if(oldUnit.equals("eth")) {
            //ethToGwei
            //ethToWei
        }
        else if(oldUnit.equals("gwei")) {
            //gweiToEth
            //gweiToWei
        }
        else if(oldUnit.equals("wei")) {
            //weiToEth
            //weiToGwei
        }

        return value;
    }

    public void notifyGet(int result) {
        getFragment.notifyResult(result);
    }
}