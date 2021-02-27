package com.example.caveavinmmm.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImaggaClient {
    private static Retrofit retro = null;
    private static String BASE_URL = "https://api.imagga.com/v2/";

    public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();
        if (retro == null) {
            retro = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }

    public static UploadApis getApiServices()
    {
        return  getClient().create(UploadApis.class);
    }
}
