package com.school.lenovo.bounter.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.school.lenovo.bounter.Bean.IntegrityTask;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.BitmapCache;

import java.util.List;

/**
 * Created by lenovo on 2016/12/9.
 */

public class ApplyUserAdapter extends BaseAdapter{
    ImageLoader imageLoader;
    List<IntegrityTask.ITData.ApplyUser> applyUserList;
    Context context;
    public ApplyUserAdapter(List<IntegrityTask.ITData.ApplyUser> applyUserList,Context context){
        this.applyUserList = applyUserList;
        this.context = context;
        RequestQueue queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue,new BitmapCache());
    }
    @Override
    public int getCount() {
        return applyUserList.size();
    }
    
    @Override
    public Object getItem(int position) {
        return applyUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ApplyViewHolder holder;
        if (convertView == null){
            view = View.inflate(context, R.layout.applyuser_container,null);
            holder = new ApplyViewHolder();
            holder.networkImageView = (NetworkImageView) view.findViewById(R.id.applyuser_iv);
            holder.username = (TextView) view.findViewById(R.id.applyuser_username);
            holder.level = (TextView) view.findViewById(R.id.applyuser_level);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ApplyViewHolder) view.getTag();
        }
        holder.networkImageView.setImageUrl(applyUserList.get(position).getPortrait(),imageLoader);
        holder.level.setText(applyUserList.get(position).getLevel());
        holder.username.setText(applyUserList.get(position).getUsername());

        return view;
    }

    class ApplyViewHolder{
        private NetworkImageView networkImageView;
        private TextView username;
        private TextView level;
    }
}
