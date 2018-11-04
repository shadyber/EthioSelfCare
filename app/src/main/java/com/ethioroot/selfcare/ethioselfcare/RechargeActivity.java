package com.ethioroot.selfcare.ethioselfcare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RechargeActivity extends AppCompatActivity {

    EditText txtcard;
    TextView txtcounter;




public void RecharegCard(View view){
    PhoneCaller.MakeCall("*805*"+txtcard.getText().toString()+"#",this);
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        txtcounter = findViewById(R.id.typedDigit);
        txtcard = findViewById(R.id.txtCardNumber);

        txtcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtcounter.setText("Character Length : " + txtcard.getText().toString().toCharArray().length);

            }
        });
    }
}