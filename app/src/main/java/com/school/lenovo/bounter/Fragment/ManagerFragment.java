package com.school.lenovo.bounter.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.school.lenovo.bounter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/3.
 */

public class ManagerFragment extends Fragment {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> fragmentTitle = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_all);
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar.setTitle("任务管理");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manager,container,false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.fragment_manager_tabLayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.fragment_manager_viewPager);
        setViewPager();
        setTabLayout();
        return rootView;
    }

    public void setViewPager() {
        AcceptFragment acceptFragment = new AcceptFragment();
        PublishFragment publishFragment = new PublishFragment();
        fragmentList.add(acceptFragment);
        fragmentList.add(publishFragment);
        fragmentTitle.add("已接受");
        fragmentTitle.add("已发布");

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getFragmentManager(),
                fragmentList,fragmentTitle);
        viewPager.setAdapter(customPagerAdapter);
    }

    public void setTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }
}
class CustomPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> fragmentTitle;

    public CustomPagerAdapter(android.app.FragmentManager fm,List<Fragment> fragmentList,List<String> fragmentTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fragmentTitle = fragmentTitle;
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}