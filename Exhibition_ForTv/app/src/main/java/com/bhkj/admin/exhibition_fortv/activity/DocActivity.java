package com.bhkj.admin.exhibition_fortv.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bhkj.admin.exhibition_fortv.R;
import com.itsrts.pptviewer.PPTViewer;

/**
 * Created by Administrator on 2018/10/19.
 */

public class DocActivity extends AppCompatActivity {

    private PPTViewer pptViewer;
    private Button bt_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_doc);
        initView();
        initEvent();
    }

    private void initEvent() {
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pptViewer.toggleActions();
                pptViewer.onSessionStarted();

            }
        });

    }


    private void initView() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/test.ppt";
        Log.d("DocActivity", path);

        pptViewer = (PPTViewer) findViewById(R.id.pptviewer);
        bt_next = (Button) findViewById(R.id.bt_next);

        PPTViewer pptViewer = (PPTViewer) findViewById(R.id.pptviewer);
//        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        pptViewer.setLayoutParams(params);
//        pptViewer.setMinimumHeight(1080);
//        pptViewer.setMinimumWidth(1980);

        pptViewer.setNext_img(R.drawable.ue006)
                .setPrev_img(R.drawable.ue007)
                .setSettings_img(R.drawable.ue008)
                .setZoomin_img(R.drawable.ue009)
                .setZoomout_img(R.drawable.ue010);
        pptViewer.loadPPT(this, path);
    }

}
