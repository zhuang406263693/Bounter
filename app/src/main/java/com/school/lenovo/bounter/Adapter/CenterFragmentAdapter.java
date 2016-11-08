package com.school.lenovo.bounter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.lenovo.bounter.R;

import java.util.List;

/**
 * Created by lenovo on 2016/11/8.
 */

public class CenterFragmentAdapter extends BaseAdapter{
    private List<String> stringList;
    private LayoutInflater inflater;
    public CenterFragmentAdapter(Context context, List<String> stringList){
        this.stringList=stringList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.fragment_center_list,null);
        holder.imageView = (ImageView) convertView.findViewById(R.id.center_item_iv);
        holder.textView = (TextView) convertView.findViewById(R.id.center_item_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(stringList.get(position));
        return convertView;
    }
    public class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }

}
