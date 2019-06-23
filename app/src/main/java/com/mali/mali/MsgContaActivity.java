package com.mali.mali;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Observers.InfoViewObserver;

public class MsgContaActivity extends AppCompatActivity {

    private RecyclerView rv;
    private EditText et;
    private Button btn,btn2,btn3;
    private Socket socket;
    private ArrayList<MsgConBean> list;
    private MsgContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_conta);

        rv = (RecyclerView) findViewById(R.id.rv);
        et = (EditText) findViewById(R.id.editMsgContact);
        btn = (Button) findViewById(R.id.buttonSendMsg);
        btn2=findViewById(R.id.buttonSendFile);
        btn3=findViewById(R.id.msgContaFile);
        list = new ArrayList<>();
        adapter = new MsgContaAdapter(this);

        final Handler handler = new MyHandler();

        String data= "测试消息"+(new Date().toString());
        Message message = Message.obtain();
        message.what = 1;
        message.obj = data;
        handler.sendMessage(message);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = et.getText().toString() + (new Date().toString());
                Message message = Message.obtain();
                message.what = 1;
                message.obj = data;
                handler.sendMessage(message);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.msgContaFile)
                    startActivity(new Intent(getApplication(),FileActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        InfoViewObserver.setIsChat(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        InfoViewObserver.setIsChat(false);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                MsgConBean bean = new MsgConBean(msg.obj.toString(), 1, df.format(new Date()), "我：");
                list.add(bean);

                // 向适配器set数据
                adapter.setData(list);
                rv.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(MsgContaActivity.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(manager);
            }
        }
    }
}
