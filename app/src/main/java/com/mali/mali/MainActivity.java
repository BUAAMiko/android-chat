package com.mali.mali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import communicate.XMPPSession;
import communicate.XMPPSessionFactory;
import communicate.XMPPSessionFactoryBuilder;
import shisong.FactoryBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        FactoryBuilder.getInstance(true);
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
}
