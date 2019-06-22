package com.mali.mali;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LogoutActivity extends Fragment implements View.OnClickListener{

    public static LogoutActivity newInstance(){
        LogoutActivity fragment=new LogoutActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activity_logout,container,false);
        view.findViewById(R.id.buttonLogout).setOnClickListener(this);
        return view;
    }

    private void userLogout(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogout:
                userLogout();
                break;
        }
    }
}
