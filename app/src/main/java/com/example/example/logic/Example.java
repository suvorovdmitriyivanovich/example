package com.example.example.logic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class Example extends Application {

    public static Context context;

    public void onCreate() {
        super.onCreate();
        Example.context = getApplicationContext();
    }

    public static void exit(Activity sender){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sender.startActivity(intent);
    }

    public static Context getAppContext() {
        return Example.context;
    }
}
