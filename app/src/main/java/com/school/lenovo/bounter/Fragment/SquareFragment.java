package com.school.lenovo.bounter.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
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

import com.google.gson.JsonArray;
import com.school.lenovo.bounter.Activity.ReleaseActivity;
import com.school.lenovo.bounter.Activity.TaskActivity;
import com.school.lenovo.bounter.Adapter.SquareFragmentAdapter;
import com.school.lenovo.bounter.Bean.Task;
import com.school.lenovo.bounter.Bean.TaskListContainer;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.HttpUtil;
import com.school.lenovo.bounter.Util.RecyclerViewClickListener;
import com.school.lenovo.bounter.Util.RecyclerViewDecoration;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by lenovo on 2016/11/3.
 */

public class SquareFragment extends Fragment {
    private final int UPDATEUI = 0;
    private final int ADDITEM = 1;
    private int flag = 1;
    private int page = 0;
    private Context context;
    private List<Task> taskList = null;
    private List<Task> tempList = null;
    private SquareFragmentAdapter squareFragmentAdapter;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
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
                        squareFragmentAdapter.initItem(taskList);
                        squareFragmentAdapter.notifyDataSetChanged();
                    }
                    break;
                case ADDITEM:
                    if (tempList!=null){
                        taskList.addAll(tempList);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }else{
                        flag = 0;
                    }
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
        context = getActivity().getBaseContext();
        //抽屉的设置
//        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_square);
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
        squareFragmentAdapter = new SquareFragmentAdapter(context);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_square_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(squareFragmentAdapter);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(context,LinearLayoutManager.HORIZONTAL));
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(context, recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(context,taskList.get(position).getTid(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                intent.putExtra("tid",taskList.get(position).getTid());
                startActivity(intent);
            }

            @Override
            public void OnItemLongClick(View view, int position) {

            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItem = taskList.size();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1 == recyclerView.getAdapter().getItemCount()&&flag!=0){
                    Log.d("add","addItem");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            addItem(page);
                        }
                    }).start();
                }

            }
        });

//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //浮动按钮的设置
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fragment_square_actionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里打开添加任务的界面
                Intent intent = new Intent(getActivity(), ReleaseActivity.class);
                startActivity(intent);
                getActivity().finish();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_all);
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar.setTitle("任务中心");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.square_action_menu,menu);
    }
    private void upDateUi(){
        flag = 1;
        page = 0;
        taskList = HttpUtil.getTaskList(0,10,"time_desc",0);
        Message message = new Message();
        message.what = UPDATEUI;
        handler.sendMessage(message);
    }
    private void addItem(int page){
        tempList = HttpUtil.getTaskList(page,10,"time_desc",0);
        Message message = new Message();
        message.what = ADDITEM;
        handler.sendMessage(message);

    }

}
