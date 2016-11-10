package com.school.lenovo.bounter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.ActivityController;
import com.school.lenovo.bounter.Util.HttpUtil;

/**
 * Created by lenovo on 2016/11/10.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText password1,password2,username;
    TextView textView;
    Button sign ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityController.addActivity(this);
        textView = (TextView) findViewById(R.id.openLogin);
        username = (EditText) findViewById(R.id.activity_register_username);
        password1 = (EditText) findViewById(R.id.activity_register_password1);
        password2 = (EditText) findViewById(R.id.activity_register_password2);
        sign = (Button) findViewById(R.id.activity_register_sign);
        sign.setOnClickListener(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_register_sign:
                final String user = username.getText().toString();
                final String pasw1 = password1.getText().toString();
                final String pasw2 = password2.getText().toString();

                if (user.equals("")){
                    Toast.makeText(getApplicationContext(),"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (pasw1.equals("")|| pasw2.equals("")){
                    Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT).show();
                }else if (pasw1.equals(pasw2)){
                    Log.d("info","进入了");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String registerResponse = HttpUtil.Register(user,pasw1,pasw2);
                            switch (registerResponse){
                                case "网络出错":
                                    break;
                                case "用户名已存在":
                                    break;
                                case "注册成功":
                                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),registerResponse,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(),"输入的两次密码不同",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
