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

import buaa.jj.designpattern.Iterator.Iterator;
import buaa.jj.designpattern.filesystem.FileSystem;
import shisong.FactoryBuilder;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;

    View headerView;
    TextView textViewEmail;
    TextView textViewUsername;

    NoScrollListView taskListAll;
    NestedScrollView scrollView;
    ProgressBar loader;
    List<File> allList;

    EditText editText;
    Iterator iterator;
    FileSystem root = FactoryBuilder.getInstance(false).getFileSystemFactory().getFileSystem(false);
    FileSystem fileSystem;
    static String chatId;

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
        editText = findViewById(R.id.editsearchFile);
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

    @Override
    protected void onResume() {
        super.onResume();
        allList.clear();
    }

    public void loadListView(ListView listView, final List<File> taskList) {
        ListTask3Adapter adapter = new ListTask3Adapter(FileActivity.this, taskList);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        String type = null;
        switch (v.getId()){
            case R.id.searchFile:
                String text = editText.getText().toString();

                break;
            case R.id.picture:
                type = "Image";
                break;
            case R.id.video:
                type = "Viedo";
                break;
            case R.id.document:
                type = "Document";
                break;
            case R.id.others:
                type = "Other";
                break;
        }
    }


}
