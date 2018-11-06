package com.ethioroot.selfcare.ethioselfcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.picasso.RequestHandler;

public class PermisionRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permision_request);



        Intent backintent = new Intent();
        backintent.putExtra("somekey", "somevalue");
        setResult(1, backintent);

    }
}
