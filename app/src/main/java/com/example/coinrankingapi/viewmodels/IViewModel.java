package com.example.coinrankingapi.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coinrankingapi.models.Coin;
import com.example.coinrankingapi.models.CoinDetails;

import java.util.List;

public interface IViewModel {
    LiveData<List<Coin>> getData();

    void generateNextValue();

    MutableLiveData<CoinDetails> getCoin();

    void generateCoinDetails(String uuid);
}