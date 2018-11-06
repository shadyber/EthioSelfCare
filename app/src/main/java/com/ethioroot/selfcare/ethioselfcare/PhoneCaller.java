package com.ethioroot.selfcare.ethioselfcare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.Manifest;

public class PhoneCaller {


    public static void MakeCall(String number, Context mContext) {


        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number)));
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            Intent intent=new Intent(mContext,PermisionRequestActivity.class);

            mContext.startActivity(intent);

            return;
        }else{

           Intent intent=new Intent(mContext,PermisionRequestActivity.class);

           mContext.startActivity(intent);

        }
        mContext.startActivity(callIntent);

    }

}
