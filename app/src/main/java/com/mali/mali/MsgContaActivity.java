package com.mali.mali;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chatdp.momento.WithdrewModule;

import java.io.File;
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

import Database.DataFunction;
import Observers.InfoViewObserver;
import communicate.XMPPSession;
import shisong.FactoryBuilder;

public class MsgContaActivity extends AppCompatActivity {

    private RecyclerView rv;
    private TextView titleView;
    private EditText et;
    private String Contactname;
    private Button btn,btn2,btn3,withdrewBtn;
    private Socket socket;
    private ArrayList<MsgConBean> list;
    private MsgContaAdapter adapter;
    private XMPPSession session;
    public Handler handler = new MyHandler();
    //------------声明withdrewModule---------------
    private WithdrewModule withdrewModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_conta);
        Intent intent=getIntent();
        Contactname=intent.getStringExtra("Contactname");
        titleView=findViewById(R.id.TitleofMsgConta);
        titleView.setText(Contactname);
        rv = (RecyclerView) findViewById(R.id.rv);
        et = (EditText) findViewById(R.id.editMsgContact);
        btn = (Button) findViewById(R.id.buttonSendMsg);
        btn2=findViewById(R.id.buttonSendFile);
        btn3=findViewById(R.id.msgContaFile);
        withdrewBtn = findViewById(R.id.withdrewBtn);
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(MsgContaActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        adapter = new MsgContaAdapter(this);
        adapter.setData(list);
        rv.setAdapter(adapter);
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn3.dispatchTouchEvent(event);
                return false;
            }
        });
        //------------获取withdrewModule实例---------------
        withdrewModule = WithdrewModule.getInstance();
        session=FactoryBuilder.getInstance(false).getSession();
        session.getSingleChat(Contactname);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = et.getText().toString() + (new Date().toString());
                FactoryBuilder.getInstance(false).getSession().sendMessage(Contactname,et.getText().toString());
                DataFunction.addSendInfo(Contactname,et.getText().toString());
                Message message = Message.obtain();
                message.what = 1;
                message.obj = et.getText().toString();
                handler.sendMessage(message);
                //-------------设置发送后清空输入框---------------
                ((EditText)findViewById(R.id.editMsgContact)).setText("");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.msgContaFile) {
                    FileActivity.chatId = Contactname;
                    startActivity(new Intent(getApplication(), FileActivity.class));
                }
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
                        FactoryBuilder.getInstance(false).getSession().sendMessage(Contactname,"WITHDREW"+msg);
                        DataFunction.deleteWithdraw(Contactname,msg);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String path = getPath(this,uri);
            File file = new File(path);
            if (file.isFile()) {
                new Thread() {
                    @Override
                    public void run() {
                        session.sendFile(Contactname,path);
                    }
                }.start();
            }
        }
    }

    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
//                Log.i(TAG,"isExternalStorageDocument***"+uri.toString());
//                Log.i(TAG,"docId***"+docId);
//                以下是打印示例：
//                isExternalStorageDocument***content://com.android.externalstorage.documents/document/primary%3ATset%2FROC2018421103253.wav
//                docId***primary:Test/ROC2018421103253.wav
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
//                Log.i(TAG,"isDownloadsDocument***"+uri.toString());
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
//                Log.i(TAG,"isMediaDocument***"+uri.toString());
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"content***"+uri.toString());
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"file***"+uri.toString());
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Map> allinfo=DataFunction.allContactInfo(Contactname);
        for (Map<String,String> map: allinfo){
            if (map.get("dir").equals("0")){
                MsgConBean bean = new MsgConBean(map.get("content"), 1,map.get("time") , "我：");
                list.add(bean);
            }
            if (map.get("dir").equals("1")){
                MsgConBean bean = new MsgConBean(map.get("content"), 2,map.get("time") ,Contactname);
                list.add(bean);
            }
        }
        adapter.setData(list);
        rv.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(MsgContaActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
    }


    @Override
    protected void onResume() {
        super.onResume();
        InfoViewObserver.getInstance().setIsChat(true);
        InfoViewObserver.getInstance().setContactID(Contactname);
        InfoViewObserver.getInstance().setHandler(handler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        InfoViewObserver.getInstance().setIsChat(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        list.clear();
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
            if (msg.what == 1) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                MsgConBean bean = new MsgConBean(msg.obj.toString(), 1, df.format(new Date()), "我：");
                list.add(bean);
            } else if(msg.what == 2) {
                //---------------对方消息----------------
                String mess= ((Map<String,String>)msg.obj).get("msg");
                if(mess.length() >= 8 && mess.substring(0,8).equals("WITHDREW")) {
                    //是一条撤回消息
                    //TODO 需要接收到消息后设置MsgBean发送到此，未完成（删数据库于接收到消息时完成）
                    ListIterator<MsgConBean> it = list.listIterator(list.size());
                    while (it.hasPrevious()) {
                        MsgConBean item = it.previous();
                        if(item.getData().equals(mess.substring(8))) {
                            it.remove();
                            adapter.notifyItemRemoved(it.nextIndex() -1);
                            adapter.notifyItemRangeChanged(it.nextIndex() - 1,list.size() - 1);
                            break;
                        }
                    }
                } else {
                    //是一条普通消息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Map<String,String> map= (Map<String, String>) msg.obj;
                    MsgConBean bean = new MsgConBean(map.get("msg"), 2, df.format(new Date()), map.get("uname"));
                    list.add(bean);
                    adapter.notifyItemInserted(list.size()-1);
                }
            }

            // 向适配器set数据
            rv.scrollToPosition(list.size()-1);
        }
    }
}
