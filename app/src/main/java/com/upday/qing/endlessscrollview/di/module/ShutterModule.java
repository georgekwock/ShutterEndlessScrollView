package com.upday.qing.endlessscrollview.di.module;

import com.upday.qing.endlessscrollview.api.ImageInterface;
import com.upday.qing.endlessscrollview.di.scope.UserScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @Author Qing Guo
 * @Date 2018/7/15
 * @Description
 */
@Module
public class ShutterModule {

    @Provides
    @UserScope
    public ImageInterface provideImageInterface(Retrofit retrofit) {
        return retrofit.create(ImageInterface.class);
    }
}
