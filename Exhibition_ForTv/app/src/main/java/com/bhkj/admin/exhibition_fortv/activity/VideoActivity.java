package com.bhkj.admin.exhibition_fortv.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bhkj.admin.exhibition_fortv.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Created by Administrator on 2018/10/15.
 */

public class VideoActivity extends AppCompatActivity {

    private JzvdStd jzvdStd;
    private VideoView videoView;
    private ArrayList<File> files;
    private int localPosition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {

    }

    private void initData() {
        String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/usb";
        Log.d("VideoActivity", s);
        try {
            files = getFiles(s);
            String absolutePath = files.get(localPosition).getAbsolutePath();
            Log.d("VideoActivity", absolutePath);
            if(files.isEmpty()){
                Toast.makeText(this, "当前无文件可以播放", Toast.LENGTH_SHORT).show();
                return;
            }
            videoView.setVideoURI(Uri.parse(absolutePath));

            //开始播放视频
            videoView.start();
        } catch (Exception e) {
            Toast.makeText(this, "当前无文件可以播放", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
        }
    }


    private void initView() {


        videoView = (VideoView) findViewById(R.id.vv);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                localPosition= localPosition+1;
                if(localPosition==files.size()){
                    localPosition=0;
                }
                videoView.setVideoURI(Uri.parse(files.get(localPosition).getAbsolutePath()));

                //开始播放视频
                videoView.start();
            }
        });
        //设置视频路径
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
//

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public ArrayList<File> getFiles(String path) throws Exception {
       //目标集合fileList
        ArrayList<File> fileList = new ArrayList<File>();
        File file = new File(path);
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
                //如果这个文件是目录，则进行递归搜索
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath());
                } else {
                    //如果文件是普通文件，则将文件句柄放入集合中
                    if (fileIndex.getPath().endsWith(".mp4")) {
                        fileList.add(fileIndex);
                    }
                }
            }
        }
        return fileList;
    }
}