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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import buaa.jj.designpattern.factory.FileSystemFactory;
import communicate.XMPPSession;
import shisong.FactoryBuilder;

public class ContactActivity extends Fragment implements View.OnClickListener {

    ImageView imageView;

    View headerView;
    TextView textViewEmail;
    TextView textViewUsername;

    NoScrollListView taskListAll;
    NestedScrollView scrollView;
    ProgressBar loader;
    List<Contact> allList;
    EditText editText;

    Contact contact;

    public static ContactActivity newInstance(){
        ContactActivity fragment=new ContactActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_contact, container, false);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        allList = new ArrayList<>();
        taskListAll = (NoScrollListView) view.findViewById(R.id.taskListToday);
        view.findViewById(R.id.searchFriend).setOnClickListener(this);
        editText = view.findViewById(R.id.editMsgContact);

        XMPPSession session = FactoryBuilder.getInstance(false).getSession();
        Map<String,String> friends = session.getAllFriends();
        for (String key:friends.keySet()) {
            String id = key.substring(0,key.indexOf("@"));
            if (!id.equals(FileSystemFactory.userId)) {
                Contact contact = new Contact();
                contact.setName(id);
                allList.add(contact);
            }
        }
        //测试数据
//        Contact con1 = new Contact();
//        con1.setName("Mali");
//        con1.setPhoneNum("123456789");
//        Contact conl2=new Contact();
//        conl2.setName("Mali2");
//        allList.add(con1);
//        allList.add(conl2);

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

    public void loadListView(ListView listView, final List<Contact> taskList) {
        ListTaskAdapter adapter = new ListTaskAdapter(getActivity(), taskList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact=taskList.get(position);
                Intent intent=new Intent(getActivity(), MsgContaActivity.class);
                intent.putExtra("ContactIname",contact.getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchFriend:
                String text = editText.getText().toString();
                XMPPSession session = FactoryBuilder.getInstance(false).getSession();
                session.addFriend(text,text);
                break;
        }
    }
}
