package com.ethioroot.selfcare.ethioselfcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    public static final int MULTIPLE_PERMISSIONS = 10;


    String[] permissions = new String[]{

            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE};  //ADD WHATEVER PERMISSIONS YOU NEEDED




    public Animation animBounce,animzoomin;
    ImageView imglogo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


        animzoomin = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        imglogo=findViewById(R.id.imglogo);
        imglogo.setAnimation(animzoomin);
call_permissions();

        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 10 seconds
                    sleep(2*1000);

                    startActivity(new Intent(SplashActivity.this,MainActivity.class));


                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


    }





    private void call_permissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
        }
        return;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.\

                } else {
                    // no permissions granted.

                }
                return;
            }
        }
    }


}
