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

public class ContactActivity extends Fragment {

    ImageView imageView;

    View headerView;
    TextView textViewEmail;
    TextView textViewUsername;

    NoScrollListView taskListAll;
    NestedScrollView scrollView;
    ProgressBar loader;
    List<Contact> allList;

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

        //测试数据
        Contact con1 = new Contact();
        con1.setName("Mali");
        con1.setPhoneNum("123456789");
        Contact conl2=new Contact();
        conl2.setName("Mali2");
        allList.add(con1);
        allList.add(conl2);

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
                startActivity(new Intent(getActivity(),MsgContaActivity.class));
            }
        });
    }
}
