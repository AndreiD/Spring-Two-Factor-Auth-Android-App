package com.androidadvance.springtwofactorauth.data.remote;

import android.content.Context;
import com.androidadvance.springtwofactorauth.BaseApplication;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TheAPI {

  String BASE_URL = "https://localhost:8080";

  @POST("/register_user") Call<Object> registerUser(@Body String encrypted_text);

  @POST("/confirm") Call<Object> confirmLogin(@Body String encrypted_text);

  @POST("/reject") Call<Object> rejectLogin(@Body String encrypted_text);

  class Factory {
    private static TheAPI service;

    public static TheAPI getIstance(Context context) {
      if (service == null) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        //very important!
        //builder.certificatePinner(new CertificatePinner.Builder().add("*.androidadvance.com", "sha256/RqzElicVPA6LkKm9HblOvNOUqWmD+4zNXcRb+WjcaAE=")
        //    .add("*.xxxxxx.com", "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9ncS03bk5bjP/UXPtaY8=")
        //    .add("*.xxxxxxx.com", "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jcqVvQI0GS3GNdA=")
        //    .add("*.xxxxxxx.com", "sha256/VjLZe/p3s8JVNBCGQBZynFLdZSTIqcO0SJ8=")
        //    .build());

        if (BaseApplication.isDebuggable()) {
          HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
          interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
          builder.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder().client(builder.build()).addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
        service = retrofit.create(TheAPI.class);
        return service;
      } else {
        return service;
      }
    }
  }
}
