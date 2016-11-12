package com.school.lenovo.bounter.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration{
    private Context context;
    private Drawable mDivider;
    private int orientation;
    public static final int[] ATTRRS = new int[]{android.R.attr.listDivider};
    public RecyclerViewDecoration(Context context, int orientation) {
        this.context = context;
        final TypedArray ta = context.obtainStyledAttributes(ATTRRS);
        this.mDivider = ta.getDrawable(0);
        ta.recycle();
        this.orientation = orientation;
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.HORIZONTAL){
            drawHorizontal(c,parent);
        }
    }
    public void drawHorizontal(Canvas canvas,RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i=0;i<childCount;i++){
            final View child = parent.getChildAt(i);
            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.HORIZONTAL){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
