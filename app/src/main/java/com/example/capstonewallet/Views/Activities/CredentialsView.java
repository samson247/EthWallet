package com.example.capstonewallet.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstonewallet.R;
import com.example.capstonewallet.viewmodels.CredentialsViewModel;

/**
 * Rename introduction, give overview of Ethereum, gas, transaction, etc. and then present credentials with explanation
 */
public class CredentialsView extends AppCompatActivity {
    private CredentialsViewModel credentialsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credentials);

        String password = getIntent().getExtras().getString("password");
        String fileName = getIntent().getExtras().getString("fileName");

        Log.d("yo123", password);
        Log.d("yo123", fileName);
        credentialsViewModel = new CredentialsViewModel(password, fileName);

        TextView passwordTextView = (TextView) findViewById(R.id.textView4);
        passwordTextView.setText(password);
        Log.d("yo123", password);

        TextView publicKeyTextView = (TextView) findViewById(R.id.textView5);
        publicKeyTextView.setText(credentialsViewModel.getPublicKey());
        Log.d("yo123", fileName);

        //Etc

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CredentialsView.this, WalletView.class);
                intent.putExtra("password", credentialsViewModel.getPassword());
                intent.putExtra("fileName", credentialsViewModel.getFileName());
                startActivity(intent);
            }
        });
    }
}