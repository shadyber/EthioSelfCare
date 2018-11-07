package com.ethioroot.selfcare.ethioselfcare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class RechargeActivity extends AppCompatActivity  implements SurfaceHolder.Callback, Detector.Processor , RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;



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


                AlertDialog.Builder builder = new AlertDialog.Builder(RechargeActivity.this);

                builder.setTitle("You Like Etho Self Care ...");
                builder.setMessage("Would You mind Rating 5 Star for Ethio Self Care on Google Play Store  \n 5 Points Will Be Added to Your Score After All.");

                builder.setPositiveButton("YES Sure", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {


                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            RewardManager.AddPoint("point",5,getApplicationContext());

                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }

                    }
                });

                builder.setNegativeButton("Maybe Later", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();




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
                    sendAppItself(RechargeActivity.this);
                } catch (IOException e) {
                    String shareBody;
                    Intent sharingIntent;
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
                Intent profile =new Intent(RechargeActivity.this,ProfileAcivity.class);
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


    EditText txtcard;
    TextView txtcounter;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home=new Intent(RechargeActivity.this,MainActivity.class);
                    startActivity(home);
                    return true;
                case R.id.navigation_account_man:
                    Intent acc=new Intent(RechargeActivity.this,AccountManager.class);
                    startActivity(acc);
                    return true;
                case R.id.navigation_extra:
                    Intent extra=new Intent(RechargeActivity.this,giftApps.class);
                    startActivity(extra);
                    return  true;
                case R.id.navigation_gebeta:
                    Intent gebeta=new Intent(RechargeActivity.this,GebetaActivity.class);
                    startActivity(gebeta);
                    return true;
            }
            return false;
        }
    };

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



    private SurfaceView cameraView;
    private TextView txtView;
    private CameraSource cameraSource;

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (Exception ignored) {
                        Log.i("Ignorred :",ignored.getMessage());
                    }
                }
            }
            break;
        }
    }


    public void RecharegCard(View view){
    PhoneCaller.MakeCall("*805*"+txtcard.getText().toString()+"#",this);

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            RewardManager.AddPoint("point",1,getApplicationContext());


        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }



    }

    public void RecharegCard(String card){
        PhoneCaller.MakeCall("*805*"+card+"#",this);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            RewardManager.AddPoint("point",1,getApplicationContext());


        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3780418992794226~7630207731");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3780418992794226/6307305550");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            RewardManager.AddPoint("point",1,getApplicationContext());


        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }



        cameraView = findViewById(R.id.surface_view);
        txtView = findViewById(R.id.txtview);
        TextRecognizer txtRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!txtRecognizer.isOperational()) {
            Log.e("Main Activity", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), txtRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(this);
            txtRecognizer.setProcessor(this);
        }


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

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_account_man);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3780418992794226~7630207731");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3780418992794226/6307305550");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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
        RewardManager.AddPoint("point",reward.getAmount(),RechargeActivity.this);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        // Toast.makeText(this, "onRewardedVideoAdLeftApplication",
        //     Toast.LENGTH_SHORT).show();

        RewardManager.AddPoint("point",5,RechargeActivity.this);
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



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
                return;
            }
            cameraSource.start(cameraView.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections detections) {
        SparseArray items = detections.getDetectedItems();
        final StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < items.size(); i++)
        {
            TextBlock item = (TextBlock)items.valueAt(i);
            strBuilder.append(item.getValue());
            strBuilder.append("/");
            // The following Process is used to show how to use lines & elements as well
            for (int j = 0; j < items.size(); j++) {
                TextBlock textBlock = (TextBlock) items.valueAt(j);
                strBuilder.append(textBlock.getValue());
                strBuilder.append("/");
                for (Text line : textBlock.getComponents()) {
                    //extract scanned text lines here
                    Log.v("lines", line.getValue());
                    strBuilder.append(line.getValue());
                    // strBuilder.append("/");
                    for (Text element : line.getComponents()) {
                        //extract scanned text words here
                        Log.v("element", element.getValue());


                        txtView.setText(element.getValue());
                        if(element.getValue().matches("^[0-9]*$") && element.getValue().length() ==13){
                            final String ele=element.getValue();

                            //   strBuilder.append(element.getValue());
                            txtView.post(new Runnable() {
                                @Override
                                public void run() {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(RechargeActivity.this);

                                    builder.setTitle("Confirm");
                                    builder.setMessage("Your card Number is : "+ele);

                                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing but close the dialog

                                            txtcard.setText(ele);
                                            RecharegCard(ele);
                                            dialog.dismiss();
                                        }
                                    });

                                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();




                                }
                            });
                        }


                    }
                }
            }
        }
        // Log.v("strBuilder.toString()", strBuilder.toString());


    }

}