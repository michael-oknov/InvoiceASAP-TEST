package com.invoiceasap.testproject.dagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.invoiceasap.testproject.http.TestApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(library = true, complete = false)
public class NetworkModule {

  @Provides
  @Singleton
  @TestApiUrl
  String provideTestEndpoint() {
    return "https://s3.amazonaws.com/invoiceasap-share/";
  }

  @Provides
  @Singleton
  TestApi provideOdsApi(Retrofit restAdapter) {
    return restAdapter.create(TestApi.class);
  }

  @Provides
  @Singleton
  Retrofit provideRestAdapter(@TestApiUrl String baseUrl, OkHttpClient httpClient,
      GsonConverterFactory gsonConverterFactory) {
    Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(httpClient);
    return retrofitBuilder.build();
  }

  @Provides
  @Singleton
  OkHttpClient provideHttpClient() {
    return new OkHttpClient.Builder().build();
  }

  @Provides
  @Singleton
  GsonConverterFactory provideGsonConverterFactory() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson myGson = gsonBuilder.create();
    return GsonConverterFactory.create(myGson);
  }
}
