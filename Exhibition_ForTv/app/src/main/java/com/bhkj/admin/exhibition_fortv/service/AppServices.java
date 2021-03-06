package com.bhkj.admin.exhibition_fortv.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bhkj.admin.exhibition_fortv.ExhibitionApplication;
import com.bhkj.admin.exhibition_fortv.MainActivity;
import com.bhkj.admin.exhibition_fortv.activity.DocActivity;
import com.bhkj.admin.exhibition_fortv.activity.ImageActivity;
import com.bhkj.admin.exhibition_fortv.activity.VideoActivity;
import com.bhkj.admin.exhibition_fortv.bean.Messages;
import com.bhkj.admin.exhibition_fortv.utils.Instructions;
import com.bhkj.admin.exhibition_fortv.utils.WebConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2018/10/15.
 */

public class AppServices extends Service {

    private SocketServer mySocketServer;
    private AudioManager mAudioManager;
    private int currentPPTNum=0;
    public static int pptsize=0;
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
        EventBus.getDefault().unregister(this);
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
        EventBus.getDefault().register(this);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT < 18) { startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标

        }
           else { Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

      return super.onStartCommand(intent, flags, startId);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(Messages messageEvent) {
        Log.d("AppServices", messageEvent.getData());

      switch (messageEvent.getData()){
          case Instructions.PLAY_VIDEO:
              ExhibitionApplication.clearCurrentActivity();
              Intent intent=new Intent(this, VideoActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
              break;
          case Instructions.VOL_UP:

              mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
              break;
          case Instructions.VOL_DOWN:

              mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);

              break;
          case Instructions.OPEN_PPT:
              ExhibitionApplication.clearCurrentActivity();
              Intent intent2=new Intent(this, DocActivity.class);
              intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent2);
              break;
          case Instructions.NEXT_PPT:
              currentPPTNum=currentPPTNum+1;
              if(currentPPTNum==pptsize){
                  currentPPTNum=0;
              }
              skipppt();
              break;
          case Instructions.PRE_PPT:
              currentPPTNum=currentPPTNum-1;
              if(currentPPTNum==-1){
                  currentPPTNum=pptsize-1;
              }
              skipppt();
              break;

          case Instructions.OPEN_IMG:
              ExhibitionApplication.clearCurrentActivity();
              Intent intent3=new Intent(this, ImageActivity.class);
              intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent3);
              break;
          case Instructions.CLOSE:
              ExhibitionApplication.clearCurrentActivity();
              break;


      }
    }

    private void skipppt() {
        Log.d("AppServices", "currentPPTNum:" + currentPPTNum);
        ExhibitionApplication.clearCurrentActivity();
        Intent intent4=new Intent(this, DocActivity.class);
        intent4.putExtra("currentPage",currentPPTNum);
        intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent4);
    }

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
