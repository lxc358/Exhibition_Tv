package com.bhkj.admin.exhibition_fortv;

import android.app.Application;
import android.content.Intent;

import com.bhkj.admin.exhibition_fortv.service.AppServices;

/**
 * Created by Administrator on 2018/10/15.
 */

public class ExhibitionApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent=new Intent(this, AppServices.class);
        startService(intent);
    }

}
