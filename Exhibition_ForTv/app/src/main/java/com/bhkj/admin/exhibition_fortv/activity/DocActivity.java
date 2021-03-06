package com.bhkj.admin.exhibition_fortv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bhkj.admin.exhibition_fortv.ExhibitionApplication;
import com.bhkj.admin.exhibition_fortv.R;
import com.bhkj.admin.exhibition_fortv.bean.Messages;
import com.bhkj.admin.exhibition_fortv.service.AppServices;
import com.bhkj.admin.exhibition_fortv.utils.AppConfig;
import com.bhkj.admin.exhibition_fortv.utils.Instructions;
import com.bhkj.admin.exhibition_fortv.utils.SPUtils;
import com.bhkj.admin.exhibition_fortv.view.AppCompactPPTViewer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/10/19.
 */

public class DocActivity extends AppCompatActivity {

    private AppCompactPPTViewer pptViewer;
    private int currentPage=0;
    private ArrayList<File> files;
    private String pptpath;
    private RelativeLayout rl_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_doc);
        ExhibitionApplication.addLocalActivity(this);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        currentPage = getIntent().getIntExtra("currentPage",0);
        Log.d("DocActivity", "currentPage:" + currentPage);

//        pptpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test" ;
        pptpath = (String) SPUtils.get(this, AppConfig.UCard_PATH, "");
        pptpath = pptpath +"/PPT";
        try {

            files = getFiles(pptpath);

            if(files.isEmpty()){
                Toast.makeText(this, "当前无文件可以打开", Toast.LENGTH_SHORT).show();
                finish();

            }
            AppServices.pptsize=files.size();
            String absolutePath = files.get(currentPage).getAbsolutePath();
            Log.d("DocActivity", absolutePath);
            pptViewer.loadPPT(this, absolutePath);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(this, "当前无文件可以打开", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this );
    }

    private void initEvent() {
//
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(Messages messageEvent) {
        Log.d("AppServices", messageEvent.getData());
        switch (messageEvent.getData()) {
            case Instructions.NEXT:
//
                pptViewer.next();
                break;
            case Instructions.PRE:
                pptViewer.previous();
//
                break;

    }
    }

    private void skipToPage() {

    }

    private void initView() {
        pptViewer = (AppCompactPPTViewer) findViewById(R.id.pptviewer);

        pptViewer.zoom(40);

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
                    if (fileIndex.getPath().endsWith(".ppt")||fileIndex.getPath().endsWith(".pptx")) {
                        fileList.add(fileIndex);
                    }
                }
            }
        }
        return fileList;
    }
}
