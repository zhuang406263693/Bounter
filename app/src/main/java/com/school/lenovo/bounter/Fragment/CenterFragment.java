package com.school.lenovo.bounter.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.school.lenovo.bounter.Activity.ChangePasswordActivity;
import com.school.lenovo.bounter.Activity.LoginActivity;
import com.school.lenovo.bounter.Activity.VerifyActivity;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.UserMessage;

import java.io.IOException;
import java.net.URL;

/**
 * Created by lenovo on 2016/11/3.
 */

public class CenterFragment extends PreferenceFragment {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    Drawable drawable;
    private static final int PORTRAIT =0;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case PORTRAIT:
                    getPreferenceScreen().findPreference("nameAndPortrait").setIcon(drawable);
            }
            return false;
        }
    });
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting2);
        setHasOptionsMenu(true);
        Preference nameAndPortrait = getPreferenceScreen().findPreference("nameAndPortrait");
        nameAndPortrait.setSummary(UserMessage.username);
        Preference username = getPreferenceScreen().findPreference("username");
        username.setSummary(UserMessage.username);
        Preference sn = getPreferenceScreen().findPreference("sn");
        if (UserMessage.sn!=null&&!UserMessage.sn.equals("null")){
            sn.setSummary(UserMessage.sn);
        }
        Preference phone = getPreferenceScreen().findPreference("phone");
        if (UserMessage.phone!=null&&!UserMessage.phone.equals("null")){
            phone.setSummary(UserMessage.phone);
        }
        Preference rank = getPreferenceScreen().findPreference("rank");
        rank.setSummary(UserMessage.level);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        Preference preference = getPreferenceScreen().findPreference("setting");
//        preference.setTitle(UserMessage.username);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    drawable = Drawable.createFromStream(new URL(UserMessage.portrait).openStream(),"src");
                    Message message = new Message();
                    message.what = PORTRAIT;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().equals("letter")){
            //打开私信页面
        }else if (preference.getKey().equals("rank")){
            //打开排名页面
        }else if (preference.getKey().equals("changePassword")){
            //修改密码
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        }else if (preference.getKey().equals("about")){
            //关于我们
        }else if (preference.getKey().equals("back")){
            SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("my", Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }else if (preference.getKey().equals("sn")){
            //学号认证
            if (UserMessage.phone==null||UserMessage.phone.equals("null")){
                Intent intent = new Intent(getActivity(), VerifyActivity.class);
                startActivity(intent);
            }
        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView().findViewById(android.R.id.list);
        if (view!=null){
            view.setPadding(0,0,0,0);
        }
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_all);
        toolbar.setTitle("设置");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.release_action_menu,menu);
    }
}
