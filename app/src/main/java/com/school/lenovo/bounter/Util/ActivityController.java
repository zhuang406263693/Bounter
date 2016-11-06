package com.school.lenovo.bounter.Util;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/5.
 */

public class ActivityController {
    public static List<AppCompatActivity> activities = new ArrayList<>();
    public static void addActivity(AppCompatActivity appCompatActivity){
        activities.add(appCompatActivity);
    }
    public static void removeActivity(AppCompatActivity appCompatActivity){
        activities.remove(appCompatActivity);
    }
    public static void finishAll(){
        for (AppCompatActivity appCompatActivity:activities){
            if (!appCompatActivity.isFinishing()){
                appCompatActivity.finish();
            }
        }
    }
}
