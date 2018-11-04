package com.ethioroot.selfcare.ethioselfcare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SMSPackageActivity extends AppCompatActivity {



    final FragmentManager fragmentManager = getSupportFragmentManager();
    final Fragment smspackagfragment = new SMSPackageFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smspackage);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameMain,smspackagfragment).commit();

        }
    }
}
