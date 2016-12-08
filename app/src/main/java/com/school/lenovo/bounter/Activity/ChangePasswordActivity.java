package com.school.lenovo.bounter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.HttpUtil;

/**
 * Created by lenovo on 2016/12/6.
 */

public class ChangePasswordActivity extends AppCompatActivity{
    Toolbar toolbar;
    Button button;
    EditText oldPassword,newPassword1,newPassword2;
    private final int OK =0;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Intent intent = new Intent(ChangePasswordActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
            }
            return false;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar_change);
        button = (Button) findViewById(R.id.activity_change_bt);
        oldPassword = (EditText) findViewById(R.id.activity_change_old);
        newPassword1 = (EditText) findViewById(R.id.activity_change_password1);
        newPassword2 = (EditText) findViewById(R.id.activity_change_password2);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String op = oldPassword.getText().toString();
                final String np1 = newPassword1.getText().toString();
                final String np2 = newPassword2.getText().toString();
                if (op.equals("")||np1.equals("")||np2.equals("")){
                    Toast.makeText(getApplicationContext(),"存在空值",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String changeResponse = HttpUtil.ChangePassword(op,np1,np2);
                            if (changeResponse.equals("修改成功")){
                                Message message = new Message();
                                message.what = OK;
                                handler.sendMessage(message);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),changeResponse,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }).start();
                }
            }
        });
    }
}
