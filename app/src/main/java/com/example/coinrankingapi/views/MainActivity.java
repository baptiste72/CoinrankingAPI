package com.example.coinrankingapi.views;

import static com.example.coinrankingapi.storage.FavoriteCoin.getInstance;
import static com.example.coinrankingapi.utils.ChangeUtils.formattedChange;
import static com.example.coinrankingapi.utils.ChangeUtils.getChangeColor;
import static com.example.coinrankingapi.utils.PriceUtils.formattedPrice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coinrankingapi.CustomWindow;
import com.example.coinrankingapi.R;
import com.example.coinrankingapi.databinding.ActivityMainBinding;
import com.example.coinrankingapi.models.Coin;
import com.example.coinrankingapi.viewmodels.IViewModel;
import com.example.coinrankingapi.viewmodels.RetrofitViewModel;
import com.example.coinrankingapi.views.adapter.CoinListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends CustomWindow implements View.OnClickListener {

    public static final String UUID = "uuid";

    private ActivityMainBinding binding;
    private IViewModel viewModel;
    private CoinListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new RetrofitViewModel(getApplication());

        binding.favoriteCoin.setOnClickListener(this);
        binding.fetchCoinList.setOnClickListener(this);

        configureAdapter();

        binding.swipe.setOnRefreshListener(() -> {
            viewModel.generateNextValue();
            binding.swipe.setRefreshing(false);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getData().observe(this, coinList -> {
            manageFavoriteCoin(coinList);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.getData().removeObservers(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.favoriteCoin) {
            startDetailsActivity(getInstance().getCoinSP().getUuid());
        } else if (id == R.id.fetchCoinList) {
            viewModel.generateNextValue();
        }
    }

    /**
     * Instancie l'adapter en lui passant les données stockées dans la base et l'interface
     * En fonction de la durée du click, soit on lance l'activité DetailsActivity
     * Soit on stocke le coin en tant que favori
     */
    private void configureAdapter() {
        adapter = new CoinListAdapter(viewModel.getData(), new CoinListAdapter.CoinClickListener() {
            @Override
            public void onItemShortClicked(String uuid) {
                startDetailsActivity(uuid);
            }

            @Override
            public void onItemLongClicked(Coin coin) {
                setFavoriteCoin(coin);
            }
        });

        binding.coinListRV.setLayoutManager(new LinearLayoutManager(this));
        binding.coinListRV.setAdapter(adapter);
    }

    /**
     * Configure la card view chargée d'afficher le coin favori
     *
     * @param coinList Liste de coins reçue depuis la réponse API
     */
    private void manageFavoriteCoin(final List<Coin> coinList) {
        // S'il n'y a pas eu de résultat retourné par l'API
        if (coinList.size() == 0) {
            binding.favoriteCoin.setVisibility(View.INVISIBLE);
        } else {
            binding.favoriteCoin.setVisibility(View.VISIBLE);

            // S'il n'y pas de coin favori enregistré dans les SharedPreferences
            if (getInstance().getCoinSP() == null) {
                // On enregistre le premier de la liste
                setFavoriteCoin(coinList.get(0));
            } else {
                for (Coin coin : coinList) {
                    if (coin.getName().equals(getInstance().getCoinSP().getName())) {
                        // On met à jour les valeurs du coin présent dans les SharedPreferences
                        setFavoriteCoin(coin);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Écrit dans les SharedPreferences l'objet Coin à sauvegarder
     * @param coin Coin à stocker dans les SharedPreferences
     */
    private void setFavoriteCoin(final Coin coin) {
        // On stocke le coin favori dans les SharedPreferences
        getInstance().setCoinSP(coin);
        setFavoriteCoinView();
    }

    /**
     * Récupère le coin favori depuis les SharedPreferences et rempli les champs de la card view
     */
    private void setFavoriteCoinView() {
        final Coin coin = getInstance().getCoinSP();

        binding.name.setText(coin.getName());
        binding.price.setText(formattedPrice(coin.getPrice()));
        binding.change.setTextColor(getChangeColor(coin.getChange()));
        binding.change.setText(formattedChange(coin.getChange()));
        Picasso.get().load(coin.getIconUrl().replace(".svg", ".png")).into(binding.icon);
    }

    /**
     * Lance l'activité DetailsActivity en lui passant un uuid
     * @param uuid L'uuid du coin sélectionné
     */
    private void startDetailsActivity(final String uuid) {
        final Intent detailsActivity = new Intent(this, DetailsActivity.class);

        detailsActivity.putExtra(UUID, uuid);
        startActivity(detailsActivity);
    }
}