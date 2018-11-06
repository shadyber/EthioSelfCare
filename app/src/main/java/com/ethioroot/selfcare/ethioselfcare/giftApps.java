package com.ethioroot.selfcare.ethioselfcare;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class giftApps extends AppCompatActivity implements  RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;
    private AdView mAdView;
    public Animation animBounce;
    private RecyclerView recyclerView;
    private AppsAdapter adapter;
    private List<AppsMenu> menuList;

    private InterstitialAd mInterstitialAd;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home=new Intent(giftApps.this,MainActivity.class);
                    startActivity(home);
                    return true;
                case R.id.navigation_account_man:
                    Intent acc=new Intent(giftApps.this,AccountManager.class);
                    startActivity(acc);
                    return true;
                case R.id.navigation_extra:
                    Intent extra=new Intent(giftApps.this,giftApps.class);
                    startActivity(extra);
                    return  true;
                case R.id.navigation_gebeta:
                    Intent gebeta=new Intent(giftApps.this,GebetaActivity.class);
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
                    RewardManager.AddPoint("point",1,getApplicationContext());


                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                return true;
            case  R.id.action_share:
                try {
                    sendAppItself(giftApps.this);
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
                Intent profile =new Intent(giftApps.this,ProfileAcivity.class);
                startActivity(profile);
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        // ...
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_apps);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3780418992794226~7630207731");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3780418992794226/6307305550");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                Runnable requester=new Runnable() {
                    @Override
                    public void run() {

                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                    }
                };

                Handler handler=new Handler();
                handler.postDelayed(requester,8000);

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                RewardManager.AddPoint("point",1,giftApps.this);
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.

                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        menuList = new ArrayList<>();
        adapter = new AppsAdapter(this, menuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        prepareAlbums();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_extra);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);



        loadRewardedVideoAd();
    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3780418992794226/7797478673",
                new AdRequest.Builder().build());
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
        RewardManager.AddPoint("point",reward.getAmount(),giftApps.this);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        // Toast.makeText(this, "onRewardedVideoAdLeftApplication",
        //     Toast.LENGTH_SHORT).show();

        RewardManager.AddPoint("point",5,giftApps.this);
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



    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {

        AppsMenu a = new AppsMenu("Mereja","https://play.google.com/store/apps/details?id=com.ethioroot.mereja&hl=en_US","com.ethioroot.mereja", "https://lh3.googleusercontent.com/suWTKHpGmA80WejBnX9HzIZh2dpxrkc0R6kBzWvrBf7-z4kJ6M25MW9C33bbSBAYg7Y=s180-rw");
        menuList.add(a);

        a = new AppsMenu("TelePot","https://play.google.com/store/apps/details?id=com.ethioroot.tele.etteleportal","com.ethioroot.tele.etteleportal", "https://lh3.googleusercontent.com/J_MgDbBOzo2lg-mjnUvFkgyJCQFbVQIpMH_yZ1EBQE9AAa7Bs6koe8ijM-8Pp1JNWQ=s180-rw");
        menuList.add(a);
        a = new AppsMenu("Qumar","https://play.google.com/store/apps/details?id=com.com.ethioroot.kumar.kumar","com.com.ethioroot.kumar.kumar", "https://lh3.googleusercontent.com/C9JlUj-3ioABtSNeW0GimjzLYhkVWbzs3QEYDMZuHCbCLiFj8kfRshN_KZXOBgKSqueT=s180-rw");
        menuList.add(a);
        a = new AppsMenu("Hope music","https://play.google.com/store/apps/details?id=com.ethioroot.hopeentertainment","com.ethioroot.hopeentertainment", "https://lh3.googleusercontent.com/hhjCMJIpozR0-ytF6HFM9iqFFLFwePie-m57N8AyJsvC-2fymHOK3gu72Jhg2-R7N7Vz=s180-rw");
        menuList.add(a);

        a = new AppsMenu("All In One","https://play.google.com/store/apps/details?id=com.ethioroot.facebooklite","com.ethioroot.facebooklite", "https://lh3.googleusercontent.com/jJtUhHfNMdAobWRsPZ5evnkDtBGC-17xXUjJlfazmJ-lnIIFNACurWUtI_m2MjxzPt4M=s180-rw");
        menuList.add(a);

        a = new AppsMenu("Amharic keyboard FynGeez - Ethiopia","https://play.google.com/store/apps/details?id=com.fynsystems.fyngeez","com.fynsystems.fyngeez", "https://lh3.googleusercontent.com/po1rzTYojq9GTs5wN08gMgNshdBy6Q4snnjOT9SvnnEDtIgqpY5ZAKI6cpzHaEkJsbE=s180-rw");
        menuList.add(a);


        a = new AppsMenu("Edna Mall","https://play.google.com/store/apps/details?id=com.ednamall.ednamall.ednamall2&hl=en","com.ednamall.ednamall.ednamall2", "https://lh3.googleusercontent.com/xRCupukA3T_BmJW63cNTycztEMegJ9dowiymIAOAlnY1tL-2tVIcpxFlXjuN-zFToNFP=s180-rw");
        menuList.add(a);

        a = new AppsMenu("Ethiopian Calendar","https://play.google.com/store/apps/details?id=com.shalom.calendar&rdid=com.shalom.calendar","com.shalom.calendar", "https://lh3.googleusercontent.com/tMB_0w6F-YQxn2crpViaDQH_yCU2JLx06X06aDl1pzj_r9i9j1fEJfkrQXSj3XxOTh0=s180-rw");
        menuList.add(a);


        a = new AppsMenu("Amharic English Dictionary","https://play.google.com/store/apps/details?id=com.fynsystems.engamharicdictionary","com.fynsystems.engamharicdictionary", "https://lh3.googleusercontent.com/ah3TAi3Nz8MyGTlrnDO68elXHoEYZNArMU0Us8-cimzl5jWDj_H0yPmNSmLiln6j4g=s180-rw");
        menuList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
