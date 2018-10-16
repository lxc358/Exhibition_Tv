package com.bhkj.admin.exhibition_fortv.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bhkj.admin.exhibition_fortv.utils.WebConfig;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2018/10/15.
 */

public class AppServices extends Service {

    private SocketServer mySocketServer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mySocketServer.stopServerAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    private final static int GRAY_SERVICE_ID = 1001;


    @Override

    public void onCreate() {
        Log.d("AppServices", "已启动");
        super.onCreate();
        WebConfig webConfig = new WebConfig();
        webConfig.setPort(9001);
        webConfig.setMaxParallels(10);
        mySocketServer = new SocketServer(webConfig);
        mySocketServer.startServerAsync();

    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18) { startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        }
           else { Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

      return super.onStartCommand(intent, flags, startId); }

    /**
    * 给 API >= 18 的平台上用的灰色保活手段
     */ public static class GrayInnerService extends Service {
        @Override public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

}
