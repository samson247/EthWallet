package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstonewallet.R;
import com.example.capstonewallet.viewmodels.GettingStartedViewModel;

/**
 * Rename introduction, give overview of Ethereum, gas, transaction, etc. and then present credentials with explanation
 */
public class GettingStartedView extends AppCompatActivity implements View.OnClickListener {
    private GettingStartedViewModel gettingStartedViewModel;
    private TextView learnMoreLink;
    private Button continueButton;
    private String[] credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getting_started);

        credentials = getIntent().getExtras().getStringArray("credentials");

        Log.d("yo123", "c" + credentials[0]);
        Log.d("yo123", "c" + credentials[1]);
        gettingStartedViewModel = new GettingStartedViewModel(credentials[0], credentials[1]);

        learnMoreLink = findViewById(R.id.learn_more_link);
        learnMoreLink.setOnClickListener(this);

        continueButton = findViewById(R.id.button);
        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == continueButton.getId()) {
            Intent intent = new Intent(GettingStartedView.this, WalletView.class);
            intent.putExtra("password", gettingStartedViewModel.getPassword());
            intent.putExtra("fileName", gettingStartedViewModel.getFileName());
            intent.putExtra("credentials", credentials);
            startActivity(intent);
        }
        else if(v.getId() == learnMoreLink.getId()) {
            Intent moreInfoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ethereum.org/"));
            startActivity(moreInfoIntent);
        }
    }
}