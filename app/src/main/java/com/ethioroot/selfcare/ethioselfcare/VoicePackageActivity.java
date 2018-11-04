package com.ethioroot.selfcare.ethioselfcare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VoicePackageActivity extends AppCompatActivity {



    final FragmentManager fragmentManager = getSupportFragmentManager();
    final Fragment voicepackagefragment = new VoicPackageFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_package);

     if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameMain,voicepackagefragment ).commit();

        }

    }
}
