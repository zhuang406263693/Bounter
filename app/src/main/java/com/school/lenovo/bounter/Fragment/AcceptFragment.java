package com.school.lenovo.bounter.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.school.lenovo.bounter.Activity.TaskActivity;
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
public class AcceptFragment extends Fragment {
    private View view;
    private String TAG = "AcceptFragment";
    private final int UPDATEUI = 0;
    private Context context;
    SquareFragmentAdapter squareFragmentAdapter;
    RecyclerView recyclerView;
    List<Task> taskList;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UPDATEUI:
                    if (taskList!=null) {
                        squareFragmentAdapter.initItem(taskList);
                        squareFragmentAdapter.notifyDataSetChanged();
                    }
                    break;
            }
            return false;
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"????");
//        if (view!=null){
//            ViewGroup parent = (ViewGroup) view.getParent();
//            if (parent!=null){
//                parent.removeView(view);
//            }
//            return view;
//        }
        view = inflater.inflate(R.layout.fragment_manager_accept,container,false);
        context = getActivity().getBaseContext();
        squareFragmentAdapter = new SquareFragmentAdapter(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.manager_accept_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(squareFragmentAdapter);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(context,LinearLayoutManager.HORIZONTAL));
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(context, recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(context,taskList.get(position).getTid(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                startActivity(intent);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"create");
    }
}
