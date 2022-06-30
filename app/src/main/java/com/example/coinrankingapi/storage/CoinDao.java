package com.example.coinrankingapi.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.coinrankingapi.models.Coin;

import java.util.List;

@Dao
public interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Coin> coins);

    @Query("SELECT * FROM coinList")
    LiveData<List<Coin>> getAll();
}