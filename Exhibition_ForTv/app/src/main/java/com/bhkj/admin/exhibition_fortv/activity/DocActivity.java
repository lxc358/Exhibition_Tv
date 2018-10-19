package com.bhkj.admin.exhibition_fortv.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.bhkj.admin.exhibition_fortv.R;
import com.itsrts.pptviewer.PPTViewer;

/**
 * Created by Administrator on 2018/10/19.
 */

public class DocActivity extends AppCompatActivity {

    private PPTViewer pptViewer;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_doc);
        initView();
    }

    private void initView() {
        pptViewer = (PPTViewer) findViewById(R.id.pptviewer);



    }

}
