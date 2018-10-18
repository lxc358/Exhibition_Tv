package com.bhkj.admin.exhibition_fortv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.bhkj.admin.exhibition_fortv.activity.VideoActivity;
import com.bhkj.admin.exhibition_fortv.utils.IpUtils;

public class MainActivity extends AppCompatActivity {

    private TextView tv_videotest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_videotest = (TextView) findViewById(R.id.tv_videotest);
        tv_videotest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });
        String ipAddress = IpUtils.getIPAddress(this);
        String hostIP = IpUtils.getHostIP();
        Log.d("MainActivity", ipAddress+":::"+hostIP);
    }
}
