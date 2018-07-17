package com.upday.qing.endlessscrollview.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @Author Qing Guo
 * @Date 2018/7/15
 * @Description Application Module of Dagger2
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }


    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }
}
