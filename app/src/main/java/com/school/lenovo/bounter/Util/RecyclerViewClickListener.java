package com.school.lenovo.bounter.Util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener{
    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public interface OnItemClickListener{
        void OnItemClick(View view, int position);
        void OnItemLongClick(View view, int position);
    }
    public RecyclerViewClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener){
        mListener=listener;
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (childView!=null&&mListener!=null){
                    mListener.OnItemClick(childView,recyclerView.getChildLayoutPosition(childView));
                    return true;
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (childView!=null&&mListener!=null){
                    mListener.OnItemLongClick(childView,recyclerView.getChildLayoutPosition(childView));
                }
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetector.onTouchEvent(e)) {
            return true;
        }else
            return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
