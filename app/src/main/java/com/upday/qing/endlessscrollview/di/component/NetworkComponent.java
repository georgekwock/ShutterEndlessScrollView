package com.upday.qing.endlessscrollview.di.component;

import com.squareup.picasso.Picasso;
import com.upday.qing.endlessscrollview.di.module.ApplicationModule;
import com.upday.qing.endlessscrollview.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * @Author Qing Guo
 * @Date 2018/7/15
 * @Description component of the network of Dagger2
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {
    Retrofit retrofit();

    Picasso picasso();
}
