package com.bhkj.admin.exhibition_fortv.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bhkj.admin.exhibition_fortv.MainActivity;

/**
 * Created by Administrator on 2018/10/23.
 */

public class BootReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction().toString();
        Log.d("BootReciver", action);
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent in=new Intent(context, MainActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);
        }
    }
}
