package com.upday.qing.endlessscrollview.api;

/**
 * @Author Qing Guo
 * @Date 2018/7/14
 * @Description Base method of the listener
 */
public interface BaseListener {
    void onError(String header, String message);

    void onNext();
}
