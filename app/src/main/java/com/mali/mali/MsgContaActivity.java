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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private class MyHandler extends Handler {

    }
}
