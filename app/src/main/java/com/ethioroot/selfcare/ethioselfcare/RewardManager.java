package com.ethioroot.selfcare.ethioselfcare;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class RewardManager {
static SharedPreferences sharedPreferences;
    public static int point=0;
    public static String name="Your Name";


    public static void SaveInt(String key, int value,Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        point=value;
    }
    public static int LoadInt(String key ,Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        point = sharedPreferences.getInt(key, 0);
        return point;
    }
//_____________________________________________

    public static void SaveString(String key, String value,Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        name=value;
    }
    public static String LoadString(String key ,Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        name = sharedPreferences.getString(key, "<Your Name>");
        return name;
    }


    //____________________________



    public static void AddPoint(String key,int value, Context context){
        point=LoadInt(key,context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, point+value);
        editor.commit();
    }


}
