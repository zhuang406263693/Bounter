package com.school.lenovo.bounter.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.school.lenovo.bounter.R;

/**
 * Created by lenovo on 2016/11/4.
 */
//已接受任务信息（在ManagerFragment下）
public class AcceptFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_accept,container,false);
        return view;
    }
}
