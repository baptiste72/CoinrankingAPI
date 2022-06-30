package com.example.coinrankingapi.views;

import static com.example.coinrankingapi.utils.ChangeUtils.formattedChange;
import static com.example.coinrankingapi.utils.ChangeUtils.getChangeColor;
import static com.example.coinrankingapi.utils.PriceUtils.formattedPrice;
import static com.example.coinrankingapi.views.MainActivity.UUID;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;

import com.example.coinrankingapi.CustomWindow;
import com.example.coinrankingapi.databinding.ActivityDetailBinding;
import com.example.coinrankingapi.models.CoinDetails;
import com.example.coinrankingapi.viewmodels.IViewModel;
import com.example.coinrankingapi.viewmodels.RetrofitViewModel;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends CustomWindow {

    private ActivityDetailBinding binding;
    private IViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new RetrofitViewModel(getApplication());
        viewModel.generateCoinDetails(getExtraUuid());
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getCoin().observe(this, coinDetails -> {
            setView(coinDetails, new LineData(buildDataSet(coinDetails)));
            binding.info.setOnClickListener(v -> openUrl(coinDetails.getCoinrankingUrl()));
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.getCoin().removeObservers(this);
    }

    /**
     * Retourne l'uuid envoyé depuis la MainActivity
     *
     * @return L'uuid ou une chaine vide
     */
    private String getExtraUuid() {
        final String uuid = getIntent().getStringExtra(UUID);
        return (uuid != null) ? uuid : "";
    }

    /**
     * Ouvre une page web vers l'URL du coin
     *
     * @param coinRankingUrl L'URL de la page web du coin sélectionné
     */
    private void openUrl(final String coinRankingUrl) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(coinRankingUrl)));
    }

    /**
     * Construit le jeu de données de la sparkline
     *
     * @param coinDetails Coin sélectionné depuis la MainActivity
     * @return Le jeu de données
     */
    private LineDataSet buildDataSet(final CoinDetails coinDetails) {
        final List<Entry> entries = new ArrayList<>();
        float index = 0f;
        final String label = "Price (US Dollar)";

        for (String value : coinDetails.getSparkline()) {
            // On associe une valeur en abscisse à chacun des prix
            entries.add(new Entry(index, Float.parseFloat(value)));
            index++;
        }

        // Création du graphe
        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setColor(Color.WHITE);

        return dataSet;
    }

    /**
     * Rempli les différents champs de la vue
     *
     * @param coinDetails Coin sélectionné depuis la MainActivity
     * @param lineData    Le jeu de données créé
     */
    private void setView(final CoinDetails coinDetails, final LineData lineData) {
        binding.name.setText(coinDetails.getName());
        binding.price.setText(formattedPrice(coinDetails.getPrice()));
        binding.change.setTextColor(getChangeColor(coinDetails.getChange()));
        binding.change.setText(formattedChange(coinDetails.getChange()));
        Picasso.get().load(coinDetails.getIconUrl().replace(".svg", ".png")).into(binding.icon);

        // Permet de rendre les liens actifs
        binding.description.setMovementMethod(LinkMovementMethod.getInstance());
        binding.description.setText(Html.fromHtml(coinDetails.getDescription(), Html.FROM_HTML_MODE_LEGACY));

        binding.linechart.setData(lineData);
        binding.linechart.getXAxis().setEnabled(false);
        binding.linechart.getAxisRight().setEnabled(false);
        binding.linechart.getAxisLeft().setEnabled(false);
        binding.linechart.getLineData().setDrawValues(false);
        binding.linechart.getDescription().setEnabled(false);
        binding.linechart.setTouchEnabled(false);
        binding.linechart.getLegend().setTextSize(12f);
        binding.linechart.getLegend().setTextColor(Color.WHITE);
        binding.linechart.animateX(1000);
        binding.linechart.invalidate();
    }
}