package com.mali.mali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.jivesoftware.smackx.iqregister.AccountManager;

//import buaa.jj.designpattern.factory.FileSystemFactory;
import communicate.XMPPSession;
import shisong.FactoryBuilder;

public class SignupActivity extends AppCompatActivity  implements View.OnClickListener{

    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail2);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        findViewById(R.id.buttonLogin2).setOnClickListener(this);
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
//            return false;
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
        AccountManager.sensitiveOperationOverInsecureConnectionDefault(true);
        XMPPSession session = FactoryBuilder.getInstance(false).getSession();
        if (session.register(email,password)) {
            if (session.login(email,password)) {
//                FileSystemFactory.userId = email;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonLogin2:
                boolean state = userLogin();
                if (state) {
                    finish();
                    startActivity(new Intent(this, MenuActivity.class));
                }
                break;
        }
    }
}
