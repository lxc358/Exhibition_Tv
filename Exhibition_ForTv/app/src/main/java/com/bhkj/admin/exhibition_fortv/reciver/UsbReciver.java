package com.bhkj.admin.exhibition_fortv.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bhkj.admin.exhibition_fortv.service.AppServices;
import com.bhkj.admin.exhibition_fortv.utils.AppConfig;
import com.bhkj.admin.exhibition_fortv.utils.SPUtils;

/**
 * Created by Administrator on 2018/10/18.
 */

public class UsbReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("UsbReciver", action);
        if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
            String mountPath = intent.getData().getPath();
            Log.d("UsbReciver", "mountPath = "  + mountPath);
            if (!TextUtils.isEmpty(mountPath)) {
                //读取到U盘路径再做其他业务逻辑

                SPUtils.put(context, AppConfig.UCard_PATH,mountPath);

            }
        } else if (action.equals(Intent.ACTION_MEDIA_UNMOUNTED) || action.equals(Intent.ACTION_MEDIA_EJECT)) {


        } else if (action.equals("android.intent.action.BOOT_COMPLETED")) {
//            Intent intent2=new Intent(context, AppServices.class);
//            context.startService(intent2);
            Toast.makeText(context, "接受到开机广播", Toast.LENGTH_LONG).show();

        }
    }
}
