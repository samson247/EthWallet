package com.example.capstonewallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.capstonewallet.databinding.FragmentTransactionBinding;

// Last button in navigation bar will load this fragment
public class AccountFragment extends Fragment {
    private TextView password;
    private TextView publicKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);

        Log.d("yo123", "oncreateview AccountFragment");

        Switch showHideSwitch = (Switch) view.findViewById(R.id.switch1);
        showHideSwitch.setOnCheckedChangeListener(this::onCheckChanged);

        Button button = (Button) view.findViewById(R.id.button7);

        button.setOnTouchListener(this::onTouch);

        password = (TextView) view.findViewById(R.id.textView7);
        publicKey = (TextView) view.findViewById(R.id.textView8);

        return view;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("yo123", "ontouch" + motionEvent.getAction());

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            password.setTransformationMethod(null);
            Log.d("yo123", "press");
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            password.setTransformationMethod(new PasswordTransformationMethod());
            Log.d("yo123", "release");
        }
        return false;
    }

    private void onCheckChanged(CompoundButton showHideSwitch, boolean state) {
        if (state == true) {
            //password.setInputType(InputType.TYPE_CLASS_TEXT);
            password.setTransformationMethod(null);
        } else if (state == false) {
            Log.d("yo123", "doing it");
            //password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

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
