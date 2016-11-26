package com.school.lenovo.bounter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.school.lenovo.bounter.Bean.Task;
import com.school.lenovo.bounter.Fragment.SquareFragment;
import com.school.lenovo.bounter.R;

import java.util.List;


/**
 * Created by lenovo on 2016/11/11.
 */

public class SquareFragmentAdapter extends RecyclerView.Adapter{
    private final int TYPE_BOTTOM = 0;
    private final int TYPE_ITEM =1;
    private List<Task> taskList;
    private LayoutInflater inflater;
    public SquareFragmentAdapter(Context context){
        this.taskList = null;
        inflater = LayoutInflater.from(context);
    }
    public void initItem(List<Task> tasks){
        this.taskList = tasks;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            return new SquareViewHolder(inflater.inflate(R.layout.task_container,parent,false));
        }else if (viewType == TYPE_BOTTOM){
            return new FooterViewHolder(inflater.inflate(R.layout.task_footer,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SquareViewHolder){
            SquareViewHolder squareViewHolder = (SquareViewHolder) holder;
            squareViewHolder.place.setText(taskList.get(position).getAddress());
            squareViewHolder.money.setText(taskList.get(position).getReward());
            squareViewHolder.time.setText(taskList.get(position).getTime());
            squareViewHolder.title.setText(taskList.get(position).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1==getItemCount()){
            return TYPE_BOTTOM;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (taskList!=null)
            return taskList.size()+1;
        else
            return 0;
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
    class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
