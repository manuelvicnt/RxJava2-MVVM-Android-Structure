package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.mock;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class RetrofitFactory {

    public static Retrofit getAdapter() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MockInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://www.mock.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
