package com.example.coinrankingapi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coinrankingapi.models.Coin;
import com.example.coinrankingapi.models.CoinDetails;
import com.example.coinrankingapi.models.ListResponse;
import com.example.coinrankingapi.models.SingleResponse;
import com.example.coinrankingapi.network.RetrofitNetworkManager;
import com.example.coinrankingapi.storage.DataRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitViewModel extends AndroidViewModel implements IViewModel {

    private final DataRepository dataRepository;
    private final LiveData<List<Coin>> data;

    private final MutableLiveData<CoinDetails> detailsData = new MutableLiveData<>();

    public RetrofitViewModel(Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        data = dataRepository.getData();
    }

    @Override
    public LiveData<List<Coin>> getData() {
        return data;
    }

    @Override
    public void generateNextValue() {
        RetrofitNetworkManager.coinRankingAPI.getCoinList().enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse> call, @NonNull Response<ListResponse> response) {
                if (response.body() != null) {
                    handleResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public MutableLiveData<CoinDetails> getCoin() {
        return detailsData;
    }

    @Override
    public void generateCoinDetails(String uuid) {
        RetrofitNetworkManager.coinRankingAPI.getCoinDetails(uuid).enqueue(new Callback<SingleResponse>() {
            @Override
            public void onResponse(@NonNull Call<SingleResponse> call, @NonNull Response<SingleResponse> response) {
                if (response.body() != null)
                    handleResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SingleResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void handleResponse(ListResponse response) {
        dataRepository.insertData(response.getData().getCoins());
    }

    private void handleResponse(SingleResponse response) {
        detailsData.postValue(response.getData().getCoin());
    }
}