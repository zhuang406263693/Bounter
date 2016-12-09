package com.school.lenovo.bounter.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.school.lenovo.bounter.Adapter.ApplyUserAdapter;
import com.school.lenovo.bounter.Bean.IntegrityTask;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.BitmapCache;
import com.school.lenovo.bounter.Util.DrawerTint;
import com.school.lenovo.bounter.Util.HttpUtil;
import com.school.lenovo.bounter.Util.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo on 2016/12/8.
 */

public class TaskActivity extends AppCompatActivity{
    ListView apply;
    Button accept;
    TextView title,content,label,money,click;
    ImageView shine1,shine2,shine3,shine4,shine5;
    NetworkImageView image;
    Toolbar toolbar;
    private List<IntegrityTask.ITData.ApplyUser> applyUserList = new ArrayList<>();
    private String tid;
    private final int OK = 0;
    private Intent intent;
    private IntegrityTask integrityTask;
    private ImageLoader imageLoader;
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
        setContentView(R.layout.activity_task_container2);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        imageLoader = new ImageLoader(queue,new BitmapCache());

        toolbar = (Toolbar) findViewById(R.id.toolbar_task);
        apply = (ListView) findViewById(R.id.task_container_apply_user);
        accept = (Button) findViewById(R.id.task_container_accept);
        title = (TextView) findViewById(R.id.task_container_title);
        content = (TextView) findViewById(R.id.task_container_content);
        label = (TextView) findViewById(R.id.task_container_label);
        money = (TextView) findViewById(R.id.task_container_money);
        click = (TextView) findViewById(R.id.task_container_click);
        image = (NetworkImageView) findViewById(R.id.task_container_image);
        shine1 = (ImageView) findViewById(R.id.task_container_shine1);
        shine2 = (ImageView) findViewById(R.id.task_container_shine2);
        shine3 = (ImageView) findViewById(R.id.task_container_shine3);
        shine4 = (ImageView) findViewById(R.id.task_container_shine4);
        shine5 = (ImageView) findViewById(R.id.task_container_shine5);
        intent =getIntent();
        tid = intent.getStringExtra("tid");
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
    private void setImageTint(ImageView imageView,ColorStateList colorStateList){
        Drawable drawable = imageView.getDrawable();
        imageView.setImageDrawable(DrawerTint.tintDrawable(drawable,colorStateList));
    }
    private void declearUi(){
        int rank = Integer.parseInt(integrityTask.getData().getRank());
        Log.d("TaskActivity", String.valueOf(rank));
        switch (rank){
            case 5:
                Log.d("info","???????????????");
                setImageTint(shine1,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine2,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine3,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine4,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine5,ColorStateList.valueOf(Color.YELLOW));
                break;
            case 4:
                setImageTint(shine1,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine2,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine3,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine4,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine5,ColorStateList.valueOf(Color.GRAY));
                break;
            case 3:
                setImageTint(shine1,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine2,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine3,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine4,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine5,ColorStateList.valueOf(Color.GRAY));
                break;
            case 2:
                setImageTint(shine1,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine2,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine3,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine4,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine5,ColorStateList.valueOf(Color.GRAY));
                break;
            case 1:
                setImageTint(shine1,ColorStateList.valueOf(Color.YELLOW));
                setImageTint(shine2,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine3,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine4,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine5,ColorStateList.valueOf(Color.GRAY));
                break;
            default:
                setImageTint(shine1,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine2,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine3,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine4,ColorStateList.valueOf(Color.GRAY));
                setImageTint(shine5,ColorStateList.valueOf(Color.GRAY));
                break;
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        click.setText('('+integrityTask.getData().getClick()+')');
        title.setText(integrityTask.getData().getTitle());
        content.setText(integrityTask.getData().getContent());
        label.setText(integrityTask.getData().getLabel());
        money.setText(integrityTask.getData().getReward());
        image.setImageUrl(integrityTask.getData().getImage()[0],imageLoader);
        Collections.addAll(applyUserList,integrityTask.getData().getApply_user());
//        Log.d("taskactivity","size is "+applyUserList.get(1).getUsername());
        apply.setAdapter(new ApplyUserAdapter(applyUserList,getApplicationContext()));
        ListUtil.setListViewHeightBasedOnChildren(apply);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String result = HttpUtil.taskApply(tid);
                        if (result.equals("申请成功")){
                            Intent intent = new Intent(TaskActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
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
        });
    }
}
