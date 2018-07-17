package com.upday.qing.endlessscrollview;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.upday.qing.endlessscrollview.di.component.DaggerNetworkComponent;
import com.upday.qing.endlessscrollview.di.component.DaggerShutterComponent;
import com.upday.qing.endlessscrollview.di.component.NetworkComponent;
import com.upday.qing.endlessscrollview.di.component.ShutterComponent;
import com.upday.qing.endlessscrollview.di.module.ApplicationModule;
import com.upday.qing.endlessscrollview.di.module.NetworkModule;
import com.upday.qing.endlessscrollview.di.module.ShutterModule;
import com.upday.qing.endlessscrollview.utils.Constant;

/**
 * @Author Qing Guo
 * @Date 2018/7/15
 * @Description
 */
public class EndlessScrollApplication extends MultiDexApplication {
    private NetworkComponent mNetComponent;
    private ShutterComponent mShutterComponent;

    /*
     * get Application instance.
     * */
    public static EndlessScrollApplication getInstance(Context context) {
        return (EndlessScrollApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetworkComponent.builder().applicationModule(new ApplicationModule
                (this))
                .networkModule(new NetworkModule(Constant.BASE_URL))
                .build();
        mShutterComponent = DaggerShutterComponent.builder().networkComponent(mNetComponent)
                .shutterModule(new ShutterModule()).build();
    }

    public NetworkComponent getmNetComponent() {
        return mNetComponent;
    }

    public ShutterComponent getmShutterComponent() {
        return mShutterComponent;
    }
}
