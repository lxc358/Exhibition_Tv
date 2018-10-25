package com.bhkj.admin.exhibition_fortv.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bhkj.admin.exhibition_fortv.ExhibitionApplication;
import com.bhkj.admin.exhibition_fortv.R;
import com.bhkj.admin.exhibition_fortv.bean.Messages;
import com.bhkj.admin.exhibition_fortv.utils.AppConfig;
import com.bhkj.admin.exhibition_fortv.utils.Instructions;
import com.bhkj.admin.exhibition_fortv.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/10/22.
 */

public class ImageActivity extends AppCompatActivity {

    private ImageView iv_image;
    private String pptpath;
    private ArrayList<File> files;
    private int currentIMG=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        ExhibitionApplication.addLocalActivity(this);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initEvent();

    }

    private void initEvent() {

    }

    private void initData() {
        pptpath = (String) SPUtils.get(this, AppConfig.UCard_PATH, "");
        pptpath = pptpath +"/IMAGE";
        try {
            files = getFiles(pptpath);
            if(files.isEmpty()){
                Toast.makeText(this, "当前无图片可以打开，请检查U盘文件配置", Toast.LENGTH_SHORT).show();
                finish();
            }
            iv_image.setImageURI(Uri.parse(files.get(currentIMG).getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "当前无图片可以打开，请检查U盘文件配置", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(Messages messageEvent) {
        Log.d("AppServices", messageEvent.getData());
        switch (messageEvent.getData()){
            case Instructions.NEXT:
            currentIMG=currentIMG+1;
                if(currentIMG==files.size()){
                    currentIMG=0;
                }
                iv_image.setImageURI(Uri.parse(files.get(currentIMG).getAbsolutePath()));
                break;
            case Instructions.PRE:
                currentIMG=currentIMG-1;
                if(currentIMG==-1){
                    currentIMG=files.size()-1;
                }
                iv_image.setImageURI(Uri.parse(files.get(currentIMG).getAbsolutePath()));
                break;

        }

    }
    private void initView() {
        iv_image = (ImageView) findViewById(R.id.iv_image);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this );
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
                    if (fileIndex.getPath().endsWith(".jpg")||fileIndex.getPath().endsWith(".png")) {
                        fileList.add(fileIndex);
                    }
                }
            }
        }
        return fileList;
    }
}
