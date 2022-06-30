package com.example.coinrankingapi.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetworkManager {

    public static final CoinRankingAPI coinRankingAPI;
    private static final String BASE_URL = "https://coinranking1.p.rapidapi.com";

    static {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new AuthInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coinRankingAPI = retrofit.create(CoinRankingAPI.class);
    }
}