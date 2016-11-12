package com.school.lenovo.bounter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.school.lenovo.bounter.Bean.Task;
import com.school.lenovo.bounter.R;

import java.util.List;


/**
 * Created by lenovo on 2016/11/11.
 */

public class SquareFragmentAdapter extends RecyclerView.Adapter{
    private List<Task> taskList;
    private LayoutInflater inflater;
    private Context context;
    public SquareFragmentAdapter(Context context,List<Task> taskList){
        this.taskList = taskList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SquareViewHolder(inflater.inflate(R.layout.task_container,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SquareViewHolder squareViewHolder = (SquareViewHolder) holder;
        squareViewHolder.place.setText(taskList.get(position).getAddress());
        squareViewHolder.money.setText(taskList.get(position).getReward());
        squareViewHolder.time.setText(taskList.get(position).getTime());
        squareViewHolder.title.setText(taskList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    class SquareViewHolder extends RecyclerView.ViewHolder{
        TextView title,time,place,money;
        public SquareViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.task_title);
            time = (TextView) itemView.findViewById(R.id.task_time);
            place = (TextView) itemView.findViewById(R.id.task_place);
            money = (TextView) itemView.findViewById(R.id.task_money);
        }
    }
}
