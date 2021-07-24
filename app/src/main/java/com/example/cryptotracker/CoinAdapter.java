package com.example.cryptotracker;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// on below line we are creating our adapter class
// in this class we are passing our array list
// and our View Holder class which we have created.

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.ViewHolder> {
    Context context;
    List<CoinModel> items;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    StorageReference storageReference;

    public CoinAdapter(Context context, ArrayList<CoinModel> items) {
        this.context = context;
        this.items = items;
    }

    public void filterList(ArrayList<CoinModel> filterList){
        items=filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        View view = LayoutInflater.from(context).inflate(R.layout.coin_layout, parent, false);
        return new CoinAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data to our item of
        // recycler view and all its views.

        CoinModel model = items.get(position);

        holder.name.setText(" "+model.getName());
        holder.price.setText("$ " + df2.format(model.getPrice_usd()));
        holder.symbol.setText(model.getSymbol()+"  |");

        Picasso.get().load(new StringBuilder("https://github.com/panditsneha/CryptoTracker/blob/master/app/src/main/res/drawable/").append(model.getSymbol().toLowerCase()).append(".png").append("?raw=true").toString()).into(holder.coinIcon);
    }

    @Override
    public int getItemCount() {
        // on below line we are returning
        // the size of our array list.
        return items.size();
    }

    // on below line we are creating our view holder class
    // which will be used to initialize each view of our layout file.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,symbol;
        ImageView coinIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // on below line we are initializing all
            // our text views along with  its ids.
            name = itemView.findViewById(R.id.coin_name);
            price = itemView.findViewById(R.id.priceUsdText);
            symbol = itemView.findViewById(R.id.coin_symbol);
            coinIcon = itemView.findViewById(R.id.coin_icon);

        }
    }
}
