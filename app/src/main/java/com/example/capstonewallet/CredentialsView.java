package com.example.capstonewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CredentialsView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credentials);

        String[] credentials = getIntent().getExtras().getStringArray("credentials");


        TextView passwordTextView = (TextView) findViewById(R.id.textView4);
        passwordTextView.setText(credentials[0]);
        Log.d("yo123", credentials[0]);

        TextView publicKeyTextView = (TextView) findViewById(R.id.textView5);
        publicKeyTextView.setText(credentials[1]);
        Log.d("yo123", credentials[1]);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CredentialsView.this, WalletView.class);
                startActivity(intent);
            }
        });
    }
}