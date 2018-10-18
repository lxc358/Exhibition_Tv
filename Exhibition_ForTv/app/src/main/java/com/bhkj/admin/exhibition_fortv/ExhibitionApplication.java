package com.bhkj.admin.exhibition_fortv;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.bhkj.admin.exhibition_fortv.service.AppServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/15.
 */

public class ExhibitionApplication extends Application {
    private static List<Activity>activities;

    @Override
    public void onCreate() {
        super.onCreate();
        activities=new ArrayList<>();

        Intent intent=new Intent(this, AppServices.class);
        startService(intent);
    }
    public static void addLocalActivity(Activity activity){
        activities.add(activity);
    }
}
