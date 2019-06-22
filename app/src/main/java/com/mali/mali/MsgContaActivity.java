package com.mali.mali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

public class MsgContaActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_conta);

        findViewById(R.id.buttonSendMsg).setOnClickListener(this);
        findViewById(R.id.buttonSendFile).setOnClickListener(this);
        findViewById(R.id.msgContaFile).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSendMsg:

                break;
            case R.id.buttonSendFile:

                break;
            case R.id.msgContaFile:
                startActivity(new Intent(this, FileActivity.class));
                break;
        }
    }
}
