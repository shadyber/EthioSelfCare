package com.ethioroot.selfcare.ethioselfcare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class LoginActivity extends AppCompatActivity {

    EditText txtname,txtphon;
    ImageView btnSave;


    private AdView mAdView;

    public   void getPoint(View view){

        Intent intent=new Intent(LoginActivity.this,AdsActivity.class);
        startActivity(intent);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAdView = findViewById(R.id.adviewsmart);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);



        txtname=findViewById(R.id.name);
        txtphon=findViewById(R.id.phone);

        btnSave=findViewById(R.id.edit);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=txtname.getText().toString();
                String phone=txtphon.getText().toString();
                RewardManager.SaveString("name",name,LoginActivity.this);
                RewardManager.SaveString("phone",phone,LoginActivity.this);
                Intent intent=new Intent(LoginActivity.this,ProfileAcivity.class);
                startActivity(intent);

            }
        });


    }
}
