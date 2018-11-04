package com.ethioroot.selfcare.ethioselfcare;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

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
      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              imglogo.setAnimation(animBounce);

              Intent mainIntent=new Intent(SplashActivity.this,MainActivity.class);
              startActivity(mainIntent);
              finish();
          }
      },1500);



    }
}
