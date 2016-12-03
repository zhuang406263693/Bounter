package com.school.lenovo.bounter.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.school.lenovo.bounter.R;

/**
 * Created by lenovo on 2016/11/3.
 */

public class CenterFragment extends PreferenceFragment {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.centerpreference);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_all);
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
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
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().equals("setting")){
//            Intent intent = new Intent(getActivity().getApplicationContext(),SettingActivity.class);
//            startActivity(intent);
            return true;
        }else if (preference.getKey().equals("temp5")){
            Log.d("info","!!!!!!!!!!1");
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
    }
}
