package com.ruan.mygitignore;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable mLine;
    private final int orientation;
    public MyItemDecoration(Context context, int orientation) {
        this.orientation = orientation;
        int[] attrs = new int[]{android.R.attr.listDivider};//系统的分割线颜色
        TypedArray a = context.obtainStyledAttributes(attrs);
        mLine = a.getDrawable(0);
        a.recycle();
    }

    /**在Item绘制之前被调用，用于绘制间隔样式*/
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation==RecyclerView.HORIZONTAL){
            drawVertical(c, parent, state);
        }else if (orientation==RecyclerView.VERTICAL){
            drawHorizontal(c, parent, state);
        }
    }
    /**onDraw和onDrawOver2个其中一个就行*/

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
    /**设置item的偏移量，偏移的部分用于填充间隔样式，即设置分割线的宽、高；在RecyclerView的onMesure()中会调用该方法。*/

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == RecyclerView.HORIZONTAL) {
            //画垂直线
            outRect.set(0, 0, mLine.getIntrinsicWidth(), 0);
        } else if (orientation == RecyclerView.VERTICAL) {
            //画水平线
            outRect.set(0, 0, 0, mLine.getIntrinsicHeight());
        }
    }

    /**
     * 画垂直分割线
     */
    private void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getRight();
            int top = child.getTop();
            int right = left + mLine.getIntrinsicWidth();
            int bottom = child.getBottom();
            mLine.setBounds(left, top, right, bottom);
            mLine.draw(c);
        }
    }


    /**
     * 画水平分割线
     */
    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getLeft();
            int top = child.getBottom();
            int right = child.getRight();
            int bottom = top + mLine.getIntrinsicHeight();
            mLine.setBounds(left, top, right, bottom);
            mLine.draw(c);
        }
    }

}
