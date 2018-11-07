package com.ethioroot.selfcare.ethioselfcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmergencyActivity extends AppCompatActivity {


    public Animation animBounce;
    private RecyclerView recyclerView;
    private PackagesAdapter adapter;
    private List<PackagesMenu> menuList;

    private InterstitialAd mInterstitialAd;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home=new Intent(EmergencyActivity.this,MainActivity.class);
                    startActivity(home);
                    return true;
                case R.id.navigation_account_man:
                    Intent acc=new Intent(EmergencyActivity.this,AccountManager.class);
                    startActivity(acc);
                    return true;
                case R.id.navigation_extra:
                    Intent extra=new Intent(EmergencyActivity.this,giftApps.class);
                    startActivity(extra);
                    return  true;
                case R.id.navigation_gebeta:
                    Intent gebeta=new Intent(EmergencyActivity.this,GebetaActivity.class);
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


                AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyActivity.this);

                builder.setTitle("You Like Etho Self Care ...");
                builder.setMessage("Would You mind Rating 5 Star for Ethio Self Care on Google Play Store  \n 5 Points Will Be Added to Your Score After All.");

                builder.setPositiveButton("YES Sure", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {


                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
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
                Toast.makeText(EmergencyActivity.this,"No Ads inside Emergency Activiy for safety",Toast.LENGTH_LONG).show();

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                return true;
            case  R.id.action_share:
                try {
                    sendAppItself(EmergencyActivity.this);
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
                Intent profile =new Intent(EmergencyActivity.this,ProfileAcivity.class);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        // ...
        return true;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);


        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3780418992794226~7630207731");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3780418992794226/6307305550");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        recyclerView =   findViewById(R.id.recycler_view);


        menuList = new ArrayList<>();
        adapter = new PackagesAdapter(EmergencyActivity.this, menuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(EmergencyActivity.this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new EmergencyActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();


        animBounce = AnimationUtils.loadAnimation(EmergencyActivity.this,
                R.anim.bounce);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_extra);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {

        menuList.clear();
        int[] covers = new int[]{
                R.drawable.emergency,
                R.drawable.data,
                R.drawable.sms};

        PackagesMenu a = new PackagesMenu("Police","991","Police",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Police","+251115512744","Federal Police",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Police","+251111559122","Addis Ababa Police",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Police","+251115528222","Traffic Police",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","991","Ethiopian Electric Power Corporation (EEPCo)",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0114431478","South Addis Ababa ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0114431478","South Addis Ababa ",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0112-595657","North Addis Ababa ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0113490585","West Addis Ababa ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0111262105","Central Addis Ababa ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Information","997","Information ",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Information","998","International Information ",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Addis Ababa Ambulance Service","991","Ambulance Service ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Addis Ababa Ambulance Service","+251111234272","Minilik Hospital",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Addis Ababa Ambulance Service","+251111115348","Municipality ",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Red Cross society","907","Red Cross society ",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Addis Ababa Fire Brigades","991","Addis Ababa Fire Brigades",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Addis Ababa Fire Brigades","+251111123341","First station (Arada) ",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Addis Ababa Fire Brigades","+251114160279","Second station( kirkos)",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Addis Ababa Fire Brigades","+251112134239","Third station (Addis Ketema)",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Addis Ababa Fire Brigades","+251114425563","Fourth station ( Lafto) ",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Addis Ababa Fire Brigades","+251114340096","Fifth staion (Akaki Kality) ",  covers[0]);
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
