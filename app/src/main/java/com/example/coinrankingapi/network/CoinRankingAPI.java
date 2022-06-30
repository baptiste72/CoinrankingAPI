package com.example.coinrankingapi.network;

import com.example.coinrankingapi.models.ListResponse;
import com.example.coinrankingapi.models.SingleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoinRankingAPI {
    // URL de l'endpoint Get Coins
    @GET("/coins?timePeriod=24h&tiers=1&orderDirection=desc&limit=50&offset=0")
    Call<ListResponse> getCoinList();

    // URL de l'endpoint Get Coin
    @GET("/coin/{uuid}?referenceCurrencyUuid=yhjMzLPhuIDl&timePeriod=24h")
    Call<SingleResponse> getCoinDetails(@Path("uuid") String uuid);
}