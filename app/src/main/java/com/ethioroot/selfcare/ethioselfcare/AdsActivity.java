package com.ethioroot.selfcare.ethioselfcare;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.io.IOException;

public class AdsActivity extends AppCompatActivity  implements RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;

    private AdView mAdView,mAdView2;
    private InterstitialAd mInterstitialAd;
    public static void sendAppItself(Activity paramActivity) throws IOException {
        PackageManager pm = paramActivity.getPackageManager();
        ApplicationInfo appInfo;
        try {
            appInfo = pm.getApplicationInfo(paramActivity.getPackageName(),
                    PackageManager.GET_META_DATA);
            Intent sendBt = new Intent(Intent.ACTION_SEND);
            sendBt.setType("*/*");
            sendBt.putExtra(Intent.EXTRA_STREAM,
                    Uri.parse("file://" + appInfo.publicSourceDir));

            paramActivity.startActivity(Intent.createChooser(sendBt,
                    "Share mereja using"));
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Package Not Found: ",e1.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        // ...
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_like:

                String shareBody = "Downlolad Ethio Self Care From Google Play  : https://play.google.com/store/apps/details?id=com.ethioroot.mereja";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Manage Your Mobile Account and all Ethio Telecom Services ");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share This Massage Using "));
                RewardManager.AddPoint("point",5,getApplicationContext());

                return true;
            case R.id.action_reward:
                showRewardVideo();

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                return true;
            case  R.id.action_share:
                try {
                    sendAppItself(AdsActivity.this);
                } catch (IOException e) {

                    shareBody = "Downlolad Ethio Self Care From Google Play  : https://play.google.com/store/apps/details?id=com.ethioroot.mereja";
                    sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Manage Your Mobile Account and all Ethio Telecom Services ");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share This Massage Using "));
                    RewardManager.AddPoint("point",5,getApplicationContext());

                    RewardManager.AddPoint("point",1,getApplicationContext());


                    Log.e("Error : ",e.getMessage());
                }
                return true;
            case R.id.action_profile:
                Intent profile =new Intent(AdsActivity.this,ProfileAcivity.class);
                startActivity(profile);
                return true;
            default:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                return super.onOptionsItemSelected(item);
        }

    }


public void loadProfile(View view){
        Intent intent=new Intent(AdsActivity.this,ProfileAcivity.class);
        startActivity(intent);

}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);




        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3780418992794226~7630207731");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3780418992794226/6307305550");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);







        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        mAdView2 = new AdView(this);
        mAdView2.setAdSize(AdSize.SMART_BANNER);








    // Use an activity context to get the rewarded video instance.
    mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);



    loadRewardedVideoAd();
}
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3780418992794226/7797478673",
                new AdRequest.Builder().build());
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void showRewardVideo(){

        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }


    @Override
    public void onRewarded(RewardItem reward) {
        //     Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
        //    reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
        RewardManager.AddPoint("point",reward.getAmount(),AdsActivity.this);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        // Toast.makeText(this, "onRewardedVideoAdLeftApplication",
        //     Toast.LENGTH_SHORT).show();

        RewardManager.AddPoint("point",5,AdsActivity.this);
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //  Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(this, "Reward Video Fail to Load ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdLoaded() {
       showRewardVideo();
        Toast.makeText(this, "Reward Video is Read To Play Tab on The Diamond icon play", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onRewardedVideoAdOpened() {
        //  Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        //  Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        // Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

}
