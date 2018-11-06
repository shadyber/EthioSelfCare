package com.ethioroot.selfcare.ethioselfcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class ProfileAcivity extends AppCompatActivity {

    TextView point,name,level;
    private AdView mAdView;
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
