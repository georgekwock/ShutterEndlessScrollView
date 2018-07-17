package com.upday.qing.endlessscrollview.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.upday.qing.endlessscrollview.EndlessScrollApplication;
import com.upday.qing.endlessscrollview.R;
import com.upday.qing.endlessscrollview.api.BaseListener;
import com.upday.qing.endlessscrollview.api.ImageInterface;
import com.upday.qing.endlessscrollview.model.Footer;
import com.upday.qing.endlessscrollview.model.Item;
import com.upday.qing.endlessscrollview.model.ShutterImages;
import com.upday.qing.endlessscrollview.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Author Qing Guo
 * @Date 2018/7/10
 * @Description View Model for ShutterImageListActivity of Shutter Image
 */
public class ShutterListModel extends ViewModel implements ModelLifeCycle {
    public ObservableBoolean showInfo;
    public ObservableBoolean showFirstProgress;
    public ObservableBoolean showRecyclerView;
    public ObservableField<String> infoMessage;

    @Inject
    ImageInterface imageInterface;

    private ShutterImageListener listener;
    private List<Item> list;
    private boolean hasMore = true;
    private int pageNo = 1;
    private int retryCount = 3;
    private Context context;

    public ShutterListModel(Context context, ShutterImageListener listener) {
        this.listener = listener;
        this.list = new ArrayList<>();
        this.context = context;
        showInfo = new ObservableBoolean(false);
        showFirstProgress = new ObservableBoolean(true);
        showRecyclerView = new ObservableBoolean(false);
        infoMessage = new ObservableField<>("");
        EndlessScrollApplication.getInstance(context).getmShutterComponent().inject(this);
    }

    /*
     * check whether need to load more data
     * */
    public boolean isHasMore() {
        return hasMore && !(list.get(list.size() - 1) instanceof Footer);
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    /***
     *  click method on retry button when error
     * @param view
     */
    public void retry_clicked(View view) {
        showInfo.set(false);
        showFirstProgress.set(true);
        loadShutterImages();
    }

    /*
     * load shutterstock images
     * */
    public void loadShutterImages() {
        if (!showFirstProgress.get()) {
            list.add(new Footer());
            if (listener != null) {
                listener.onUpdate(list);
            }
        }
        imageInterface.getImages(pageNo, Constant.PER_PAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ShutterImages>() {
                    @Override
                    public void onCompleted() {
                        if (pageNo == 1) {
                            showFirstProgress.set(false);
                        }
                        showRecyclerView.set(!showInfo.get() && !showFirstProgress.get());
                        pageNo++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!showFirstProgress.get()) {
                            int size = list.size();
                            list.remove(size - 1);
                            if (listener != null) {
                                listener.onUpdate(list);
                            }
                        }
                        //retry if still not ok, display error message.
                        if (retryCount != 0) {
                            retryCount--;
                            loadShutterImages();
                        } else {
                            if (listener != null && !showFirstProgress.get()) {
                                listener.onError(context.getResources().getString(R.string
                                        .error_head), context.getResources().getString(R.string
                                        .error_msg));
                            } else {
                                showFirstProgress.set(true);
                                infoMessage.set(context.getResources().getString(R.string
                                        .error_retry));
                                showInfo.set(true);
                            }
                        }
                    }

                    @Override
                    public void onNext(ShutterImages shutterImages) {
                        if (shutterImages.getData().size() > 0) {
                            retryCount = 3;
                            if (!showFirstProgress.get()) {
                                int size = list.size();
                                list.remove(size - 1);
                            }
                            list.addAll(shutterImages.getData());
                            if (listener != null) {
                                listener.onUpdate(list);
                            }
                        } else {
                            hasMore = false;
                            if (listener != null) {
                                listener.onReachEnd();
                            }
                        }
                    }
                });
    }


    @Override
    public void onResume() {
        if (listener != null) {
            listener.onInit(list);
        }
        loadShutterImages();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        listener = null;
        list = null;
    }

    /***
     * Listener to for the method of the list
     */
    public interface ShutterImageListener extends BaseListener {
        void onUpdate(List<Item> data);

        void onInit(List<Item> data);

        void onReachEnd();
    }
}
