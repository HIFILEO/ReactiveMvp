package com.example.reactivemvp.dagger;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;


import com.example.reactivemvp.application.ReactiveMvpApplication;
import com.example.reactivemvp.service.InfoServiceApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Dagger2 {@link Module} providing application-level dependency bindings.
 */
@Module
public class ApplicationModule {
    private ReactiveMvpApplication application;

    public ApplicationModule(ReactiveMvpApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context context() {
        return application;
    }

    /**
     * Getter for the Application class.
     *
     * @return the Application
     */
    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Resources provideResources() {
        return application.getResources();
    }

    @Singleton
    @Provides
    public Gson provideGson(GsonBuilder builder) {
        return builder.create();
    }

    @Provides
    public GsonBuilder provideGsonBuilder() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder;
    }

    @Singleton
    @Provides
    public OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder, HttpLoggingInterceptor.Level level) {
        //Log HTTP request and response data in debug mode
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);

        return builder.build();
    }

    @Singleton
    @Provides
    public Retrofit.Builder provideRetrofitBuilder(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor.Level provideHttpLoggingInterceptorLevel() {
        return getHttpLoggingInterceptorLevel();
    }

    HttpLoggingInterceptor.Level getHttpLoggingInterceptorLevel() {
        return HttpLoggingInterceptor.Level.NONE;
    }

    @Singleton
    @Provides
    @Named("ioSched")
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Singleton
    @Provides
    @Named("computeSched")
    Scheduler provideComputeScheduler() {
        return Schedulers.computation();

    }

    @Singleton
    @Provides
    @Named("androidSched")
    Scheduler provideAndroidScheduler() {
        return AndroidSchedulers.mainThread();

    }

    @Provides
    @Singleton
    public InfoServiceApi provideInfoServiceApi(Retrofit.Builder retrofit) {
        return retrofit.baseUrl("https://api.myjson.com/").build().create(InfoServiceApi.class);
    }
}
