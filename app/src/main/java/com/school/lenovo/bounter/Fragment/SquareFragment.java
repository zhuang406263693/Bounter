package com.school.lenovo.bounter.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.school.lenovo.bounter.Adapter.SquareFragmentAdapter;
import com.school.lenovo.bounter.Bean.Task;
import com.school.lenovo.bounter.Bean.TaskListContainer;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.HttpUtil;
import com.school.lenovo.bounter.Util.RecyclerViewClickListener;
import com.school.lenovo.bounter.Util.RecyclerViewDecoration;

import java.util.List;

/**
 * Created by lenovo on 2016/11/3.
 */

public class SquareFragment extends Fragment{
    private final int UPDATEUI = 0;
    private Context context;
    private List<Task> taskList = null;
    DrawerLayout drawer;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UPDATEUI:
                    //更新ui
                    if (taskList!=null){
                        Log.d("info","进入了");
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//                        squareFragmentAdapter = new SquareFragmentAdapter(context,taskList);
                        recyclerView.setAdapter(new SquareFragmentAdapter(context,taskList));
                    }
                    break;
            }
            return false;
        }
    });
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_square,container,false);
        setHasOptionsMenu(true);
        context = getContext();
        //抽屉的设置
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_square);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_square_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        upDateUi();
                    }
                }).start();
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_square_recyclerview);
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
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        //浮动按钮的设置
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fragment_square_actionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里打开添加任务的界面

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                upDateUi();
            }
        }).start();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.square_action_menu,menu);
    }
    private void upDateUi(){
        taskList = HttpUtil.getTaskList(0,10,"time_desc",0);
        Message message = new Message();
        message.what = UPDATEUI;
        handler.sendMessage(message);
    }
}
