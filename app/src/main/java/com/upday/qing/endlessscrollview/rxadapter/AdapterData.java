package com.upday.qing.endlessscrollview.rxadapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.upday.qing.endlessscrollview.EndlessScrollApplication;
import com.upday.qing.endlessscrollview.R;
import com.upday.qing.endlessscrollview.databinding.ListItemBinding;
import com.upday.qing.endlessscrollview.model.DataDetail;
import com.upday.qing.endlessscrollview.model.Footer;
import com.upday.qing.endlessscrollview.model.Item;

import java.util.List;

import rx.functions.Action1;

/**
 * @Author Qing Guo
 * @Date 2018/7/15
 * @Description class contains method of data process.
 */
public class AdapterData {
    private final int TYPE_FOOTER = 0;
    private final int TYPE_ITEM = 1;
    private Picasso picasso;
    private Context context;
    private RecyclerView recyclerView;
    private List<Item> mDataSet;
    private ShutterAdapter<Item> mAdapter;

    public AdapterData(Context context, List<Item> mDataSet) {
        this.mDataSet = mDataSet;
        this.context = context;
        picasso = EndlessScrollApplication.getInstance(context).getmNetComponent().picasso();
    }

    public AdapterData updateDataSource(List<Item> dataSet) {
        mDataSet = dataSet;
        return this;
    }

    /*
     * judge whether this scroll view is sliding to the end.
     * */
    public boolean isScrolledEnd() {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public ShutterAdapter<Item> getmAdapter() {
        return mAdapter;
    }

    public AdapterData bindRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null)
            throw new NullPointerException("null recyclerview not permitted!");
        this.recyclerView = recyclerView;
        bindLayoutManager();
        bindAdapter();
        return this;
    }

    private void bindAdapter() {
        //new Adater and distinguish the Item and Footer.
        mAdapter = new ShutterAdapter<>(mDataSet, new GetViewType() {
            @Override
            public int getViewType(int position) {
                return (mDataSet.get(position) instanceof Footer) ? TYPE_FOOTER : TYPE_ITEM;
            }
        });
        recyclerView.setAdapter(mAdapter);
        /*
         *   bind adapter and require for the shutter img using Picasso
         * */
        mAdapter.asObservable().subscribe(new Action1<TypeViewHolder<Item>>() {
            @Override
            public void call(TypeViewHolder<Item> itemTypeViewHolder) {
                ViewDataBinding dataBinding = itemTypeViewHolder.getmViewDataBinding();
                if (dataBinding != null && dataBinding instanceof ListItemBinding) {
                    ListItemBinding lb = (ListItemBinding) dataBinding;
                    DataDetail detail = (DataDetail) itemTypeViewHolder.getItem();
                    if (detail != null && detail.getAssets() != null && detail.getAssets()
                            .getPreview() != null) {
                        String shutterId = detail.getId();
                        String shutterDes = detail.getDescription();
                        picasso.load(Uri.parse(detail.getAssets().getPreview().getUrl()))
                                .fit()
                                .centerCrop()
                                .noFade()
                                .placeholder(R.mipmap.ic_launcher)
                                .into(lb.shutterPoster);
                        lb.shutterDesc.setText(shutterDes);
                        lb.shutterId.setText(shutterId);
                    } else {
                        picasso.load(R.mipmap.ic_launcher);
                    }
                }
            }
        });
    }

    /***
     * Set the Recyclerview Scroll listener
     *
     * @param listener
     */
    public void setScrollListener(RecyclerView.OnScrollListener listener) {
        if (listener != null) {
            this.recyclerView.addOnScrollListener(listener);
        }
    }

    private void bindLayoutManager() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setmAdapter() {
        if (mAdapter != null) {
            mAdapter.updateList(mDataSet);
        }
    }
}
