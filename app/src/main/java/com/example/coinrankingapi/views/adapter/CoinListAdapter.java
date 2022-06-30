package com.example.coinrankingapi.views.adapter;

import static com.example.coinrankingapi.utils.ChangeUtils.formattedChange;
import static com.example.coinrankingapi.utils.ChangeUtils.getChangeColor;
import static com.example.coinrankingapi.utils.PriceUtils.formattedPrice;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinrankingapi.databinding.CoinCellBinding;
import com.example.coinrankingapi.models.Coin;
import com.squareup.picasso.Picasso;

import java.util.Comparator;
import java.util.List;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinViewHolder> {

    private final LiveData<List<Coin>> coinList;
    private final CoinClickListener listener;

    public CoinListAdapter(LiveData<List<Coin>> coinList, CoinClickListener listener) {
        this.coinList = coinList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CoinViewHolder(CoinCellBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        if (coinList.getValue() != null) {
            // Tri de la liste en fonction du rank de chaque coin
            coinList.getValue().sort(Comparator.comparingInt(Coin::getRank));
            holder.bindData(coinList.getValue().get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (coinList != null && coinList.getValue() != null) {
            return coinList.getValue().size();
        }

        return 0;
    }

    public interface CoinClickListener {
        void onItemShortClicked(String uuid);

        void onItemLongClicked(Coin coin);
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder {

        private final CoinCellBinding binding;

        private CoinViewHolder(CoinCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindData(final Coin coin) {
            setView(coin);

            binding.getRoot().setOnClickListener(v -> {
                // Lorsqu'un appui est effectué sur un coin, on envoie son uuid à la MainActivity
                listener.onItemShortClicked(coin.getUuid());
            });

            binding.getRoot().setOnLongClickListener(v -> {
                // Lorsqu'un appui long est effectué sur un coin, on l'envoie à la MainActivity
                listener.onItemLongClicked(coin);
                return true;
            });
        }

        /**
         * Rempli les différents champs de la vue
         *
         * @param coin Coin de la liste coinList
         */
        private void setView(final Coin coin) {
            binding.name.setText(coin.getName());
            binding.price.setText(formattedPrice(coin.getPrice()));
            binding.change.setTextColor(getChangeColor(coin.getChange()));
            binding.change.setText(formattedChange(coin.getChange()));
            Picasso.get().load(coin.getIconUrl().replace(".svg", ".png")).into(binding.icon);
        }
    }
}