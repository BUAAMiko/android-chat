package com.mali.mali;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import buaa.jj.designpattern.factory.FileSystemFactory;
import communicate.XMPPSession;
import communicate.XMPPSessionFactory;
import communicate.XMPPSessionFactoryBuilder;
import shisong.FactoryBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        FactoryBuilder.getInstance(true);
        FileSystemFactory.savePath= getApplicationContext().getExternalCacheDir().getAbsolutePath();
        checkPermission();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/chat/");
        if (!file.isDirectory()) {
            System.out.println("mkdir:" + file.mkdirs());
        }
        System.out.println("hasdir:" + file.isDirectory());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textViewSignup:
                startActivity(new Intent(this, SignupActivity.class));
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    private void checkPermission() {
        // Storage Permissions
        final int REQUEST_EXTERNAL_STORAGE = 1;


        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            System.out.println("permission ok");
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/chat/");
            if (!file.isDirectory())
                System.out.println("mkdir:" + file.mkdirs());
            System.out.println("hasdir:" + file.isDirectory());
        }
    }
}
