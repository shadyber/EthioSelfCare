package com.ethioroot.selfcare.ethioselfcare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DataPackageActivity extends AppCompatActivity {



    final FragmentManager fragmentManager = getSupportFragmentManager();
    final Fragment datapackageFragment = new DataPackageFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_package);


        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameMain,datapackageFragment ).commit();

        }
    }
}
