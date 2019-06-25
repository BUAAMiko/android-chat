package com.mali.mali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.dell.jsltevent.ObserverService.Event.FileEvent;
import com.example.dell.jsltevent.ObserverService.Event.InfoEvent;

import org.litepal.LitePal;

import Database.DataFunction;
import Observers.FileObserver;
import Observers.InfoViewObserver;
import Observers.InfodbObserver;
import buaa.jj.designpattern.factory.FileSystemFactory;
import communicate.XMPPSession;
import shisong.FactoryBuilder;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
    }

    public void goback(View v) {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private boolean userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return false;
        }

//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Email is invalid");
//            editTextEmail.requestFocus();
//            return;
//        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return false;
        }

        if(password.length() < 6) {
            editTextPassword.setError("Password length should be at least 6 characters");
            editTextPassword.requestFocus();
            return false;
        }

        progressBar.setVisibility(View.VISIBLE);
        XMPPSession session = FactoryBuilder.getInstance(true).getSession();
        if (session.login(email,password)) {
            FileSystemFactory.userId = email;
            return true;
        }
        progressBar.setVisibility(View.INVISIBLE);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, SignupActivity.class));
                break;
            case R.id.buttonLogin:
                boolean state = userLogin();
                if (state) {
                    FileSystemFactory.userId = editTextEmail.getText().toString().trim();
                    DataFunction.UserId=FileSystemFactory.userId;
                    LitePal.getDatabase();
                    InfoEvent.getInstance().attachObserver(InfoViewObserver.getInstance());
                    InfoEvent.getInstance().attachObserver(InfodbObserver.getInstance());
                    FileEvent.getInstance().attachObserver(FileObserver.getInstance());
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
        }
    }
}
