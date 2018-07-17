package com.upday.qing.endlessscrollview.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.upday.qing.endlessscrollview.utils.Constant;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author Qing Guo
 * @Date 2018/7/15
 * @Description Network module for Dagger
 */
@Module
public class NetworkModule {
    private String mBaseUrl;

    public NetworkModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
        // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level
                .BODY));
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.header("Authorization", getBase64Creadentials(Constant.CLIENT_ID,
                        Constant.CLIENT_SECRET));
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }


    @Provides
    @Singleton
    Picasso providePicasso(Application application) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().header("Cache-Control", "max-age=" + (60 *
                        60 * 24 * 365)).build();
            }
        });
        builder.cache(new Cache(application.getCacheDir(), Integer.MAX_VALUE));
        OkHttp3Downloader okHttpDownloader = new OkHttp3Downloader(builder.build());
        return new Picasso.Builder(application).downloader(okHttpDownloader).build();
    }

    private String getBase64Creadentials(String clientId, String clientSecret) {
        String base64Credentials = Base64.encodeToString(
                (clientId + ":" + clientSecret).getBytes(),
                Base64.NO_WRAP);
        return String.format(Locale.ENGLISH, "Basic %s", base64Credentials);
    }
}
