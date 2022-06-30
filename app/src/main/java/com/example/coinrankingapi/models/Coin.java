package com.example.coinrankingapi.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coinList")
public class Coin {

    @NonNull
    @PrimaryKey
    private final String uuid;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "price")
    private final String price;

    @ColumnInfo(name = "rank")
    private final int rank;

    @ColumnInfo(name = "change")
    private final String change;

    @ColumnInfo(name = "iconUrl")
    private final String iconUrl;

    public Coin(@NonNull String uuid, String name, String price, int rank, String change, String iconUrl) {
        this.uuid = uuid;
        this.name = name;
        this.price = price;
        this.rank = rank;
        this.change = change;
        this.iconUrl = iconUrl;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getRank() {
        return rank;
    }

    public String getChange() {
        return change;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}