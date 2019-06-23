package com.mali.mali;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chatdp.momento.WithdrewModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import Observers.InfoViewObserver;

public class MsgContaActivity extends AppCompatActivity {

    private RecyclerView rv;
    private EditText et;
    private Button btn,btn2,btn3,withdrewBtn;
    private Socket socket;
    private ArrayList<MsgConBean> list;
    private MsgContaAdapter adapter;
    //------------声明withdrewModule---------------
    private WithdrewModule withdrewModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_conta);

        rv = (RecyclerView) findViewById(R.id.rv);
        et = (EditText) findViewById(R.id.editMsgContact);
        btn = (Button) findViewById(R.id.buttonSendMsg);
        btn2=findViewById(R.id.buttonSendFile);
        btn3=findViewById(R.id.msgContaFile);
        withdrewBtn = findViewById(R.id.withdrewBtn);
        list = new ArrayList<>();
        adapter = new MsgContaAdapter(this);
        //------------获取withdrewModule实例---------------
        withdrewModule = WithdrewModule.getInstance();

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
                //-------------设置发送后清空输入框---------------
                ((EditText)findViewById(R.id.editMsgContact)).setText("");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 这里是对方撤回的逻辑，目前采用模拟
                String withdrew = "WITHDREW"+((EditText)findViewById(R.id.editMsgContact)).getText().toString();
                Message m = Message.obtain();
                m.what = 2;
                m.obj = withdrew;
                handler.sendMessage(m);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.msgContaFile)
                    startActivity(new Intent(getApplication(),FileActivity.class));
            }
        });

        //---------------撤销历史-----------------
        withdrewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWithdrewDialog();
            }
        });

        //---------------长按弹窗-----------------
        rv.addOnItemTouchListener(new RecyclerViewClickListener(MsgContaActivity.this, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {}

            @Override
            public void onItemLongClick(View view, final int position) {
                //判是否是我的消息，不是不允许长按
                if(list.get(position).getNumber()!=1) return;

                AlertDialog.Builder builder = new AlertDialog.Builder(MsgContaActivity.this);
                builder.setMessage("确认撤销信息吗?");
                builder.setTitle("提示");

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String msg = list.get(position).getData();
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        // TODO 发送一条撤销消息

                        //存储撤回消息内容到withdrewModule
                        Map message = new HashMap();
                        message.put("msg", msg);    // msg表示内容
                        message.put("time", new Date());
                        withdrewModule.updateHistory(message);
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }

        }));

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

    //----------撤回历史纪录弹窗------------
    private void showWithdrewDialog() {
        final List<String> withdrewHistory = new ArrayList<>();
        //withdrewModule获取数据，对withdrewHistory填充内容（若返回为空，显示“最近三分钟无撤回”）
        withdrewModule.resetHistory();
        List<Map> withdrewMessages =  withdrewModule.getHistory();
        Iterator<Map> it = withdrewMessages.iterator();
        while (it.hasNext()) withdrewHistory.add(it.next().get("time").toString());
        if(withdrewHistory.isEmpty()) withdrewHistory.add("最近三分钟无撤回");
        //关联View
        View view = LayoutInflater.from(rv.getContext()).inflate(R.layout.withdrew_history, null);
        //设置数据源
        final ListView withdrewLV = view.findViewById(R.id.withdrew);
        withdrewLV.setAdapter(new ArrayAdapter<String>(rv.getContext(), android.R.layout.simple_list_item_1, withdrewHistory));
        // 创建PopupWindow对象，指定宽度和高度
        PopupWindow window = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWidth((int)findViewById(R.id.editMsgContact).getWidth());
        // 设置动画
//        window.setAnimationStyle(R.style.popup_window_anim);
        // 设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        // 设置可以获取焦点
        window.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // 更新popupwindow的状态
        window.update();
        // 以下拉的方式显示，并且可以设置显示的位置
//        window.showAsDropDown(tvProduct, 0, 20);
        window.showAtLocation(withdrewBtn, Gravity.LEFT | Gravity.BOTTOM, 0, 50);
        withdrewLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置对话框中内容为历史纪录
                ((EditText)findViewById(R.id.editMsgContact)).setText(withdrewHistory.get(position));
            }
        });
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                MsgConBean bean = new MsgConBean(msg.obj.toString(), 1, df.format(new Date()), "我：");
                list.add(bean);
            } else if(msg.what == 2) {
                //---------------对方消息----------------
                if(msg.obj.toString().substring(0,8).equals("WITHDREW")) {
                    //是一条撤回消息
                    //TODO 需要接收到消息后设置MsgBean发送到此，未完成（删数据库于接收到消息时完成）
                    ListIterator<MsgConBean> it = list.listIterator(list.size());
                    while (it.hasPrevious()) {
                        MsgConBean item = it.previous();
                        if(item.getData().equals(msg.obj.toString().substring(8))) {
                            it.remove();
                            break;
                        }
                    }
                } else {
                    //是一条普通消息
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                    MsgConBean bean = new MsgConBean(msg.obj.toString(), 2, df.format(new Date()), "好友：");
                    list.add(bean);
                }
            }

            // 向适配器set数据
            adapter.setData(list);
            rv.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(MsgContaActivity.this, LinearLayoutManager.VERTICAL, false);
            rv.setLayoutManager(manager);
        }
    }
}
