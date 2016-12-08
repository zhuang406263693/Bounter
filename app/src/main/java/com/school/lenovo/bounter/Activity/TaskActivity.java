package com.school.lenovo.bounter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.lenovo.bounter.Bean.IntegrityTask;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.HttpUtil;

/**
 * Created by lenovo on 2016/12/8.
 */

public class TaskActivity extends AppCompatActivity{
    ListView apply;
    Button accept;
    TextView title,content,label,money;
    ImageView image,shine1,shine2,shine3,shine4,shine5;

    private final int OK = 0;
    private Intent intent;
    private IntegrityTask integrityTask;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case OK:
                    declearUi();
            }
            return false;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_container2);

        apply = (ListView) findViewById(R.id.task_container_apply_user);
        accept = (Button) findViewById(R.id.task_container_accept);
        title = (TextView) findViewById(R.id.task_container_title);
        content = (TextView) findViewById(R.id.task_container_content);
        label = (TextView) findViewById(R.id.task_container_label);
        money = (TextView) findViewById(R.id.task_container_money);
        image = (ImageView) findViewById(R.id.task_container_image);
        shine1 = (ImageView) findViewById(R.id.task_container_shine1);
        shine2 = (ImageView) findViewById(R.id.task_container_shine2);
        shine3 = (ImageView) findViewById(R.id.task_container_shine3);
        shine4 = (ImageView) findViewById(R.id.task_container_shine4);
        shine5 = (ImageView) findViewById(R.id.task_container_shine5);
        intent =getIntent();
        final String tid = intent.getStringExtra("tid");
        //从上一层中获取消息
        new  Thread(new Runnable() {
            @Override
            public void run() {
                integrityTask = HttpUtil.getIntegrityTask(tid);
                if (integrityTask!=null){
                    Message message = new Message();
                    message.what = OK;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
    private void declearUi(){
        title.setText(integrityTask.getData().getTitle());
        content.setText(integrityTask.getData().getContent());
        label.setText(integrityTask.getData().getLabel());
        money.setText(integrityTask.getData().getReward());
    }
}
