package com.mali.mali;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import shisong.FactoryBuilder;

public class MenuActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    selectedFragment=ChatActivity.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, selectedFragment).commit();
                    return true;
                case R.id.navigation_contact:
                    selectedFragment=ContactActivity.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, selectedFragment).commit();
                    return true;
                /*case R.id.navigation_file:
                    selectedFragment=FileActivity.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, selectedFragment).commit();
                    return true;*///这里去掉了menu页面上的file页面
                case R.id.navigation_logout:
                    selectedFragment=LogoutActivity.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, selectedFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FactoryBuilder.getInstance(false).initFileSystem();
    }

}
