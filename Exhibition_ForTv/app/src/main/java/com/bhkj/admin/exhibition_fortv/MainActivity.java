package com.bhkj.admin.exhibition_fortv;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bhkj.admin.exhibition_fortv.activity.DocActivity;
import com.bhkj.admin.exhibition_fortv.activity.VideoActivity;
import com.bhkj.admin.exhibition_fortv.service.AppServices;
import com.bhkj.admin.exhibition_fortv.utils.AppConfig;
import com.bhkj.admin.exhibition_fortv.utils.IpUtils;
import com.bhkj.admin.exhibition_fortv.utils.SPUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tv_videotest;
    private EditText et_registernum;
    private Button bt_activation;
    private boolean isActivation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isActivation = (boolean) SPUtils.get(this, "isActivation", false);
        if(isActivation){

            boolean serviceWorked = isServiceWorked(this, "com.bhkj.admin.exhibition_fortv.service.AppServices");
            Log.d("MainActivity", "serviceWorked:" + serviceWorked);
            if(!serviceWorked){
            Intent intent=new Intent(this, AppServices.class);
            startService(intent);
            }
            Toast.makeText(this, "后台服务已启动", Toast.LENGTH_SHORT).show();
            finish();
            return;

        }else{

            setContentView(R.layout.activity_main);

        }

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        bt_activation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = et_registernum.getText().toString().trim();
                if(pass.equals(AppConfig.Activation_CODE)){
                    SPUtils.put(MainActivity.this,"isActivation",true);
                    Intent intent=new Intent(MainActivity.this, AppServices.class);
                    startService(intent);
                    Toast.makeText(MainActivity.this, "激活成功，程序已在后台运行", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "激活码错误，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData() {


    }


    private void initView() {

        et_registernum = (EditText) findViewById(R.id.et_registernum);
        bt_activation = (Button) findViewById(R.id.bt_activation);


    }
    public  boolean isServiceWorked(Context context, String serviceName) {
        ActivityManager myManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>)
                myManager.getRunningServices(Integer.MAX_VALUE); for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(serviceName)) {
                return true;
            } }
        return false;
    }


}
