package com.example.cryptotracker;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CoinViewHolder extends RecyclerView.ViewHolder {

    public ImageView coin_icon;
    public TextView coin_symbol,coin_name,coin_price;

    public CoinViewHolder(@NonNull View itemView) {
        super(itemView);

        coin_icon = itemView.findViewById(R.id.coin_icon);
        coin_symbol=itemView.findViewById(R.id.coin_symbol);
        coin_name = itemView.findViewById(R.id.coin_name);
        coin_price = itemView.findViewById(R.id.priceUsdText);
    }
}
