package com.mali.mali;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.LinkedList;
import java.util.List;

import buaa.jj.designpattern.Iterator.Iterator;
import buaa.jj.designpattern.factory.FileSystemFactory;
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
    List<FileSystem> allList;

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
        if (chatId == null)
            throw new RuntimeException();
        else {
            LinkedList linkedList = new LinkedList();
            fileSystem = root.getFile(linkedList,chatId);
            if (fileSystem == null) {
                linkedList.add(chatId);
                root.addFile(linkedList,null);
            }
            fileSystem = root.getFile(new LinkedList<String>(),chatId);
        }
        //测试数据
//        File fle = new File();
//        fle.setName("History123");
//        allList.add(fle);
//        File fle2 = new File();
//        fle2.setName("History456");
//        allList.add(fle2);
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

    public void loadListView(ListView listView, final List<FileSystem> taskList) {
        ListTask3Adapter adapter = new ListTask3Adapter(FileActivity.this, taskList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buaa.jj.designpattern.filesystem.File file = (buaa.jj.designpattern.filesystem.File) allList.get(position);
                Intent intent = file.open();
                getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        String type = null;
        switch (v.getId()){
            case R.id.searchFile:
                String text = editText.getText().toString();
                iterator = fileSystem.getIterator(type);
                while (iterator.hasNext()) {
                    FileSystem tmp = (FileSystem) iterator.next();
                    if (tmp.getName().contains(text)) {
                        allList.add(tmp);
                    }
                }
                return;
            case R.id.picture:
                type = "Image";
                break;
            case R.id.video:
                type = "Video";
                break;
            case R.id.document:
                type = "Document";
                break;
            case R.id.others:
                type = "Others";
                break;
        }
        iterator = fileSystem.getIterator(type);
        while (iterator.hasNext()) {
            FileSystem tmp = (FileSystem) iterator.next();
            allList.add(tmp);
        }
    }


}
