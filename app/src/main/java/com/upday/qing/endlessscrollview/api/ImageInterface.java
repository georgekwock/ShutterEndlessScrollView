package com.upday.qing.endlessscrollview.api;

import com.upday.qing.endlessscrollview.model.ShutterImages;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ImageInterface {
    @GET("images/search")
    Observable<ShutterImages> getImages(@Query("page") int page, @Query("per_Page") int perPage);
}
