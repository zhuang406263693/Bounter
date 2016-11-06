package com.school.lenovo.bounter.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.ActionMenuView;
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
    DrawerLayout drawer;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    List<android.support.v4.app.Fragment> fragmentList = new ArrayList<>();
    List<String> fragmentTitle = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manager,container,false);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_manager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.fragment_manager_tabLayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.fragment_manager_viewPager);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
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

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getChildFragmentManager(),
                fragmentList,fragmentTitle);
        viewPager.setAdapter(customPagerAdapter);
    }

    public void setTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }
}
class CustomPagerAdapter extends FragmentPagerAdapter{
    private List<android.support.v4.app.Fragment> fragmentList;
    private List<String> fragmentTitle;
    public CustomPagerAdapter(FragmentManager fm,
                              List<android.support.v4.app.Fragment> fragmentList,
                              List<String> fragmentTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fragmentTitle = fragmentTitle;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}