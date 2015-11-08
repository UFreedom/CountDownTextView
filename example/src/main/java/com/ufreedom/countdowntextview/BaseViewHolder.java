package com.ufreedom.countdowntextview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author SunMeng
 * Date : 2015 十月 29
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindView(T object);
}