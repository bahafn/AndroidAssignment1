package com.example.androidassignment1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidassignment1.DataAccess.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private final List<Item> items;
    private final AppCompatActivity activity;

    public ItemAdapter(AppCompatActivity activity, List<Item> items) {
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        CardView cardView = holder.cardView;

        ImageView imageView = cardView.findViewById(R.id.image);
        imageView.setImageDrawable(ContextCompat.getDrawable(cardView.getContext(), item.getImageID()));

        TextView txtName = cardView.findViewById(R.id.txtName);
        txtName.setText(item.getName());

        TextView txtPrice = cardView.findViewById(R.id.txtPrice);
        txtPrice.setText(String.valueOf(item.getPrice()));

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ItemActivity.class);

            intent.putExtra(ItemActivity.NAME, item.getName());
            intent.putExtra(ItemActivity.DESCRIPTION, item.getDescription());
            intent.putExtra(ItemActivity.PRICE, item.getPrice());
            intent.putExtra(ItemActivity.IMAGE, item.getImageID());
            intent.putExtra(ItemActivity.AMOUNT, item.getAmount());

            activity.startActivity(intent);
            activity.finish();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
