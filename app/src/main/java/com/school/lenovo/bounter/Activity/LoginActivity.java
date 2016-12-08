package com.school.lenovo.bounter.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.ActivityController;
import com.school.lenovo.bounter.Util.HttpUtil;
import com.school.lenovo.bounter.Util.UserMessage;

/**
 * Created by lenovo on 2016/11/4.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button login_button;
    EditText username;
    EditText password;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityController.addActivity(this);
        login_button = (Button) findViewById(R.id.activity_login_login);
        login_button.setOnClickListener(this);
        username = (EditText) findViewById(R.id.activity_login_username);
        password = (EditText) findViewById(R.id.activity_login_password);
        textView = (TextView) findViewById(R.id.openRegister);
        SharedPreferences sharedPreferences =  getSharedPreferences("my", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","null");
        String portrait = sharedPreferences.getString("portrait","null");
        String username = sharedPreferences.getString("username","null");
        String sn = sharedPreferences.getString("sn","null");
        String phone = sharedPreferences.getString("phone","null");
        String level = sharedPreferences.getString("level","null");
        if (!token.equals("null")){
            UserMessage.Token = token;
            UserMessage.portrait = portrait;
            UserMessage.username = username;
            UserMessage.sn = sn;
            UserMessage.level = level;
            UserMessage.phone = phone;
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_login:
                //获取用户名和密码，并开启线程进行信息获取
                final String user = username.getText().toString();
                final String pasw = password.getText().toString();
                if (user.equals("")) {
                    username.setText("请输入用户名");
                } else if (pasw.equals("")) {
                    password.setText("请输入密码");
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String loginResponse = HttpUtil.Login(user,pasw);
                            switch (loginResponse){
                                case "网络出错":
                                    break;
                                case "密码出错":
                                    break;
                                case "登陆成功":
                                    SharedPreferences sharedPreferences =  getSharedPreferences("my", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("token", UserMessage.Token);
                                    editor.putString("username",UserMessage.username);
                                    editor.putString("portrait",UserMessage.portrait);
                                    editor.putString("sn",UserMessage.sn);
                                    editor.putString("phone",UserMessage.phone);
                                    editor.putString("level",UserMessage.level);
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),loginResponse,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
