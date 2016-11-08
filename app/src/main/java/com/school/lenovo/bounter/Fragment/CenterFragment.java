package com.school.lenovo.bounter.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.school.lenovo.bounter.Adapter.CenterFragmentAdapter;
import com.school.lenovo.bounter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/3.
 */

public class CenterFragment extends Fragment {
    DrawerLayout drawer;
    Toolbar toolbar;
    ListView listView1,listView2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_center,container,false);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_center);
        listView1 = (ListView) rootView.findViewById(R.id.fragment_center_list1);
        listView2 = (ListView) rootView.findViewById(R.id.fragment_center_list2);
        setListView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        return rootView;
    }
    private void setListView(){
        List<String> title1 = new ArrayList<>();
        title1.add("我的关注");
        title1.add("我的收藏");
        title1.add("最近浏览");
        title1.add("我的草稿");
        title1.add("我的Live");
        listView1.setAdapter(new CenterFragmentAdapter(getContext(),title1));

        List<String> title2 = new ArrayList<>();
        title2.add("夜间模式");
        title2.add("设置");
        listView2.setAdapter(new CenterFragmentAdapter(getContext(),title2));

        setListViewHeightBasedOnChildren(listView1);
        setListViewHeightBasedOnChildren(listView2);

        //再这里添加listview的监听
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void setListViewHeightBasedOnChildren(ListView listView) {
        CenterFragmentAdapter listAdapter = (CenterFragmentAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }
}
