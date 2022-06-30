package com.example.coinrankingapi.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coinrankingapi.ExampleApplication;
import com.example.coinrankingapi.models.Coin;
import com.google.gson.Gson;

public class FavoriteCoin {

    private static final String SHARED_PREFERENCES_NAME = "CoinrankingAPI_Prefs";
    private static final String COIN_KEY = "coinKey";
    private static FavoriteCoin INSTANCE;

    private final SharedPreferences preferences;
    private final Gson gson = new Gson();

    private FavoriteCoin() {
        preferences = ExampleApplication.getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static FavoriteCoin getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FavoriteCoin();
        }

        return INSTANCE;
    }

    /**
     * Retourne le coin favori
     *
     * @return Coin favori enregistré dans les SharedPreferences
     */
    public Coin getCoinSP() {
        return gson.fromJson(preferences.getString(COIN_KEY, null), Coin.class);
    }

    /**
     * Enregistre le coin favori dans les SharedPreferences
     *
     * @param coin Objet coin à enregistrer dans les SharedPreferences
     */
    public void setCoinSP(Coin coin) {
        preferences.edit().putString(COIN_KEY, gson.toJson(coin)).apply();
    }
}