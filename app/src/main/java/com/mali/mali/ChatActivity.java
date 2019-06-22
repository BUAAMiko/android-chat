package com.mali.mali;

import android.content.Intent;
import android.os.Bundle;
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

public class ChatActivity extends Fragment {

    ImageView imageView;

    View headerView;
    TextView textViewEmail;
    TextView textViewUsername;

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

        //测试数据
        Chat developmenTeam = new Chat();//加入一个开发团队的聊天框，让allList不为空,否则无法对聊天框为null设置onclicklistener
        developmenTeam.setName("Development Team");
        allList.add(developmenTeam);
        Chat chat=new Chat();
        chat.setName("MaLiasdf");
        allList.add(chat);
        Chat chat1=new Chat();
        chat1.setName("chat2");
        allList.add(chat1);
        Chat chat2=new Chat();
        chat2.setName("MaLiasdf");
        allList.add(chat2);
        Chat chat3=new Chat();
        chat3.setName("chat2");
        allList.add(chat3);

        return view;
    }

    public void populateData() {
        //scrollView.setVisibility(View.GONE);

        //allList.clear();

        loadListView(taskListAll,allList);
    }

    @Override
    public void onStart() {
        super.onStart();

        populateData();
    }

    public void loadListView(ListView listView, final List<Chat> taskList) {
        ListTask2Adapter adapter = new ListTask2Adapter(getActivity(), taskList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(),MsgContaActivity.class));
            }
        });
    }


}
