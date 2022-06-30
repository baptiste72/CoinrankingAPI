package com.example.coinrankingapi.network;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private static final String HOST_HEADER_NAME = "x-rapidapi-host";
    private static final String HOST_HEADER_VALUE = "coinranking1.p.rapidapi.com";
    private static final String KEY_HEADER_NAME = "x-rapidapi-key";
    private static final String KEY_HEADER_VALUE = "key_header_value";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = new Request.Builder(chain.request())
                .addHeader(HOST_HEADER_NAME, HOST_HEADER_VALUE)
                .addHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE)
                .build();

        return chain.proceed(request);
    }
}