package com.school.lenovo.bounter.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.school.lenovo.bounter.Adapter.SquareFragmentAdapter;
import com.school.lenovo.bounter.Bean.Task;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.HttpUtil;
import com.school.lenovo.bounter.Util.RecyclerViewClickListener;
import com.school.lenovo.bounter.Util.RecyclerViewDecoration;

import java.util.List;

/**
 * Created by lenovo on 2016/11/4.
 */
//ManagerFragment(任务管理下的fragment)，用于已接受任务信息的管理
public class AcceptFragment extends Fragment{
    private final int UPDATEUI = 0;
    private Context context;
    RecyclerView recyclerView;
    List<Task> taskList;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UPDATEUI:
                    if (taskList!=null) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new SquareFragmentAdapter(context,taskList));
                    }
                    break;
            }
            return false;
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_accept,container,false);
        context = getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.manager_accept_rv);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(context,LinearLayoutManager.HORIZONTAL));
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(context, recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(context,taskList.get(position).getTid(),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void OnItemLongClick(View view, int position) {

            }
        }));
        new Thread(new Runnable() {
            @Override
            public void run() {
                taskList = HttpUtil.getMyReceive();
                Message message = new Message();
                message.what = UPDATEUI;
                handler.sendMessage(message);
            }
        }).start();
        return view;
    }
}
