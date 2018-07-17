package com.upday.qing.endlessscrollview.rxadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upday.qing.endlessscrollview.R;

import java.util.List;

import rx.subjects.PublishSubject;

/**
 * @Author Qing Guo
 * @Date 2018/7/11
 * @Description Shutter Adapter for RecyclerView
 */
public class ShutterAdapter<T> extends RecyclerView.Adapter<TypeViewHolder<T>> {
    private final int TYPE_FOOTER = 0;
    private final int TYPE_ITEM = 1;
    private final PublishSubject<TypeViewHolder<T>> mPublishSubject;
    private List<T> mList;
    private RecyclerView recyclerView;
    private GetViewType mViewTypeCallback;

    public ShutterAdapter(List<T> mList, GetViewType mViewTypeCallback) {
        this.mList = mList;
        this.mViewTypeCallback = mViewTypeCallback;
        mPublishSubject = PublishSubject.create();
    }

    @NonNull
    @Override
    public TypeViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_footer, parent, false);
                break;
        }
        return new TypeViewHolder<>(view);

    }

    /***
     * Get the Publish Subject as observable
     *
     * @return Observable<TypesViewHolder       <       T>>
     */
    public rx.Observable<TypeViewHolder<T>> asObservable() {
        return mPublishSubject.asObservable();
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder<T> holder, int position) {
        holder.setItem(mList.get(position));
        mPublishSubject.onNext(holder);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypeCallback.getViewType(position);
    }

    public List<T> getmList() {
        return mList;
    }

    /*
     * method used to update the list of the data.
     * */
    public void updateList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

}
