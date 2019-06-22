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

public class FileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;

    View headerView;
    TextView textViewEmail;
    TextView textViewUsername;

    NoScrollListView taskListAll;
    NestedScrollView scrollView;
    ProgressBar loader;
    List<File> allList;

    File file;

    /*public static FileActivity newInstance(){
        FileActivity fragment=new FileActivity();
        return fragment;
    }*///原来作为一个fragment的初始化

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        allList = new ArrayList<>();
        taskListAll = (NoScrollListView) findViewById(R.id.taskListToday);
        findViewById(R.id.editsearchFile).setOnClickListener(this);
        findViewById(R.id.searchFile).setOnClickListener(this);
        findViewById(R.id.picture).setOnClickListener(this);
        findViewById(R.id.video).setOnClickListener(this);
        findViewById(R.id.document).setOnClickListener(this);
        findViewById(R.id.others).setOnClickListener(this);

        //测试数据
        File fle = new File();
        fle.setName("History123");
        allList.add(fle);
        File fle2 = new File();
        fle2.setName("History456");
        allList.add(fle2);
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

    public void loadListView(ListView listView, final List<File> taskList) {
        ListTask3Adapter adapter = new ListTask3Adapter(FileActivity.this, taskList);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editsearchFile:

                break;
            case R.id.searchFile:

                break;
        }
    }


}
