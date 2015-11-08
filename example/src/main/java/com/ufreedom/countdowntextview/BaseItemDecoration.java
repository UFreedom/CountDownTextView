package com.ufreedom.countdowntextview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author UFreedom
 * Date : 2015 十月 27
 */
public  class BaseItemDecoration extends RecyclerView.ItemDecoration {

    private int mEdgeSpace;
    private int mHorizontalSpace;
    private int mVerticalSpace;
    private int mSpanCount;

    private Drawable drawable;



    /**
     * @param mEdgeSpace 最外面的边距
     * @param mHorizontalSpace 水平间距
     * @param mVerticalSpace 垂直间距
     * @param mSpanCount 列数
     */
    public BaseItemDecoration(int mEdgeSpace, int mHorizontalSpace, int mVerticalSpace, int mSpanCount) {
        this.mEdgeSpace = mEdgeSpace;
        this.mHorizontalSpace = mHorizontalSpace;
        this.mVerticalSpace = mVerticalSpace;
        this.mSpanCount = mSpanCount;
    }


    /**
     *
     * @param mEdgeSpace
     * @param mHorizontalSpace
     * @param mVerticalSpace
     * @param mSpanCount
     * @param color 间距颜色
     */
    public BaseItemDecoration(int mEdgeSpace, int mHorizontalSpace, int mVerticalSpace, int mSpanCount, int color) {
        this.mEdgeSpace = mEdgeSpace;
        this.mHorizontalSpace = mHorizontalSpace;
        this.mVerticalSpace = mVerticalSpace;
        this.mSpanCount = mSpanCount;
        drawable = new ColorDrawable(color);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (drawable == null){
            return;
        }

        final int childCount = parent.getChildCount();

        for (int position = 0; position < childCount; position++) {

            Rect rect = new Rect();

            int column = position % mSpanCount;

            final View child = parent.getChildAt(position);

            rect.left = child.getLeft() - (column == 0 ? mEdgeSpace :  mHorizontalSpace / 2 );
            rect.right = child.getRight() + (column == mSpanCount - 1 ? mEdgeSpace : mHorizontalSpace );

            //只有第一行画顶部的空间，后面的顶部空间全部以上一行的底部空间
            if (column < mSpanCount) {
                rect.top = child.getTop() ;
            }else {
                rect.top = child.getTop() - mVerticalSpace;
            }
            rect.bottom = child.getBottom() + mVerticalSpace;
            drawable.setBounds(rect);
            drawable.draw(c);
        }

    }


   


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        
        int position = parent.getChildAdapterPosition(view);
        int column = position % mSpanCount;
        outRect.left = (column == 0 ? mEdgeSpace :  mHorizontalSpace / 2 );
        outRect.right = (column == mSpanCount - 1 ? mEdgeSpace : mHorizontalSpace /2);

        //只有第一行画顶部的空间，后面的顶部空间全部以上一行的底部空间
        if (position < mSpanCount) {
            //   outRect.top = mVerticalSpace;
            outRect.top = 0;
        }
        outRect.bottom = mVerticalSpace;

    }
    
    
   // public abstract void setItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
    
}
