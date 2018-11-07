package com.ethioroot.selfcare.ethioselfcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class ProfileAcivity extends AppCompatActivity {

    TextView point,name,level;


    private AdView mAdView;


    public   void SignUp(View view){

        Intent intent=new Intent(ProfileAcivity.this,LoginActivity.class);
    startActivity(intent);

    }
    public   void getPoint(View view){

        Intent intent=new Intent(ProfileAcivity.this,AdsActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acivity);

        point=findViewById(R.id.point);
        name=findViewById(R.id.name);
        level=findViewById(R.id.level);

        RewardManager.LoadInt("point",this);
        point.setText(String.valueOf(RewardManager.point));

        RewardManager.LoadString("name",this);
        name.setText(String.valueOf(RewardManager.LoadString("name",this)));


        setTitle("Your Profile ["+RewardManager.point+" ]");

        mAdView = findViewById(R.id.adviewsmart);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);


    }
}
