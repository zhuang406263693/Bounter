package com.school.lenovo.bounter.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.school.lenovo.bounter.Fragment.CenterFragment;
import com.school.lenovo.bounter.Fragment.ManagerFragment;
import com.school.lenovo.bounter.Fragment.SquareFragment;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.ActivityController;

/**
 * Created by lenovo on 2016/11/3.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,NavigationView.OnNavigationItemSelectedListener{
    SquareFragment squareFragment;
    ManagerFragment managerFragment;
    CenterFragment centerFragment;
    BottomNavigationBar bottomNavigationBar;
    NavigationView navigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        ActivityController.addActivity(this);
        squareFragment = new SquareFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,squareFragment).commit();
        setBottomNavigationBar();
        setNavigationView();
    }

    public void setBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.main_nav_bottom);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher,"Square"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher,"Manager"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher,"Center"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    public void setNavigationView() {
        navigationView  = (NavigationView) findViewById(R.id.main_nav_drawer);
//        navigationView.getHeaderView(0).bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重启默认打开任务广场
        squareFragment = new SquareFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,squareFragment).commit();
    }

    @Override
    public void onTabSelected(int position) {
        switch (position){
            case 0:
                squareFragment = new SquareFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container,squareFragment)
                        .commit();
                break;
            case 1:
                managerFragment = new ManagerFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container,managerFragment)
                        .commit();
                break;
            case 2:
                centerFragment = new CenterFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container,centerFragment)
                        .commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    //drawer的监听，需要传入数据刷新任务广场
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_time:
                break;
            case R.id.nav_hot:
                break;
            case R.id.nav_study:
                break;
            case R.id.nav_exchange:
                break;
            case R.id.nav_team:
                break;
            case R.id.nav_appointment:
                break;
            case R.id.nav_waiting:
                break;
            case R.id.nav_emotion:
                break;

            //付款的暂时不添加！！！！！！
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
