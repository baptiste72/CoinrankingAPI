package com.example.coinrankingapi.storage;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.coinrankingapi.models.Coin;

import java.util.List;

public class DataRepository {

    private final CoinDao coinDao;
    private final LiveData<List<Coin>> data;

    public DataRepository(Context applicationContext) {
        AppDatabase database = AppDatabase.getDatabase(applicationContext);
        this.coinDao = database.coinDao();
        this.data = coinDao.getAll();
    }

    public LiveData<List<Coin>> getData() {
        return data;
    }

    public void insertData(List<Coin> coins) {
        AppDatabase.databaseWriteExecutor.execute(() -> coinDao.insert(coins));
    }
}