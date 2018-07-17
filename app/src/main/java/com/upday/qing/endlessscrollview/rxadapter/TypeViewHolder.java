package com.upday.qing.endlessscrollview.rxadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Author Qing Guo
 * @Date 2018/7/15
 * @Description parent class of typed view holder
 */
public class TypeViewHolder<T> extends RecyclerView.ViewHolder {
    private ViewDataBinding mViewDataBinding;
    private T item;

    public TypeViewHolder(View itemView) {
        super(itemView);
        mViewDataBinding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getmViewDataBinding() {
        return mViewDataBinding;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
