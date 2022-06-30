package com.example.coinrankingapi.models;

import androidx.annotation.NonNull;

import java.util.List;

public class CoinDetails extends Coin {

    private List<String> sparkline;
    private String description;
    private String coinrankingUrl;

    public CoinDetails(@NonNull String uuid, String name, String price, int rank, String change, String iconUrl) {
        super(uuid, name, price, rank, change, iconUrl);
    }

    public List<String> getSparkline() {
        return sparkline;
    }

    public String getDescription() {
        return description;
    }

    public String getCoinrankingUrl() {
        return coinrankingUrl;
    }
}