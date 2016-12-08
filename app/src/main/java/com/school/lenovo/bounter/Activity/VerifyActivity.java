package com.school.lenovo.bounter.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.school.lenovo.bounter.Util.UserMessage;

/**
 * Created by lenovo on 2016/12/6.
 */

public class VerifyActivity extends AppCompatActivity{
    Toolbar toolbar;
    Button button;
    EditText sn,password;
    private final int OK =0;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Intent intent = new Intent(VerifyActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
            }
            return false;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        button = (Button) findViewById(R.id.activity_verify_verify);
        sn = (EditText) findViewById(R.id.activity_verify_username);
        password = (EditText) findViewById(R.id.activity_verify_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar_verify);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mySn = sn.getText().toString();
                final String myPassword = password.getText().toString();
                if (myPassword.equals("")||mySn.equals("")){
                    Toast.makeText(getApplicationContext(),"存在空值",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String result = HttpUtil.snVerify(mySn,myPassword);
                            if (result.equals("认证成功")){
                                SharedPreferences sharedPreferences =  getSharedPreferences("my", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("sn",mySn);
                                UserMessage.sn = mySn;
                                Message message = new Message();
                                message.what = OK;
                                handler.sendMessage(message);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }
}
