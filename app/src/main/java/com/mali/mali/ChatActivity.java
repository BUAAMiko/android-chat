package com.mali.mali;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Database.DataFunction;
import Observers.InfoViewObserver;

public class ChatActivity extends Fragment {

    ImageView imageView;

    View headerView;
    TextView textViewEmail;
    TextView textViewUsername;
    public Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==1){
                Map<String,String> map= (Map<String, String>) msg.obj;
                boolean flag=false;
                for (Chat chat: allList){
                    if (chat.getName().equals(map.get("uname"))){
                        flag=true;
                        chat.setLastMsg(map.get("msg"));
                    }
                }
                if (flag){
                    Chat chat=new Chat();
                    chat.setName(map.get("uname"));
                    chat.setLastMsg("msg");
                    allList.add(chat);
                }
                loadListView(taskListAll,allList);
            }
            return false;
        }
    });
    NoScrollListView taskListAll;
    NestedScrollView scrollView;
    ProgressBar loader;
    List<Chat> allList;

    Chat chat;

    public static ChatActivity newInstance(){
        ChatActivity fragment = new ChatActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat, container, false);
        scrollView = (NestedScrollView)view.findViewById(R.id.scrollView);
        allList = new ArrayList<>();
        taskListAll = (NoScrollListView) view.findViewById(R.id.ChatList);
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.setTitle("聊天");
        Set<String> contacts= DataFunction.allcontacts();
        for (String contactname: contacts){
            Chat chat=new Chat();
            chat.setName(contactname);
            chat.setLastMsg(DataFunction.lastInfo(contactname));
            allList.add(chat);
        }
//        //测试数据
//        Chat developmenTeam = new Chat();//加入一个开发团队的聊天框，让allList不为空,否则无法对聊天框为null设置onclicklistener
//        developmenTeam.setName("Development Team");
//        allList.add(developmenTeam);
//        Chat chat=new Chat();
//        chat.setName("MaLiasdf");
//        chat.setLastMsg("Hello!很大");
//        allList.add(chat);
//        Chat chat1=new Chat();
//        chat1.setName("chat2");
//        allList.add(chat1);
//        Chat chat2=new Chat();
//        chat2.setName("MaLiasdf");
//        allList.add(chat2);
//        Chat chat3=new Chat();
//        chat3.setName("chat2");
//        allList.add(chat3);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        loadListView(taskListAll,allList);
    }

    @Override
    public void onResume() {
        super.onResume();
        InfoViewObserver.getInstance().setList(true);
        InfoViewObserver.getInstance().setHandler(handler);
    }

    @Override
    public void onPause() {
        super.onPause();
        InfoViewObserver.getInstance().setList(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        allList.clear();
    }

    public void loadListView(ListView listView, final List<Chat> taskList) {
        ListTask2Adapter adapter = new ListTask2Adapter(getActivity(), taskList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat=taskList.get(position);
                Intent intent=new Intent(getActivity(), MsgContaActivity.class);
                intent.putExtra("Contactname",chat.getName());
                startActivity(intent);
            }
        });
    }

}
