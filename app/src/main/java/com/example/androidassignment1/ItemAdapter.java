package com.example.androidassignment1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidassignment1.DataAccess.Cart.CartDAFactory;
import com.example.androidassignment1.DataAccess.Cart.iCartDA;
import com.example.androidassignment1.DataAccess.Item.Item;

import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    // Possible view types
    private static final int EMPTY_VIEW_TYPE = 0;
    private static final int NORMAL_VIEW_TYPE = 1;

    private final List<Item> items;
    private final AppCompatActivity activity;

    /**
     * Whether the user is in the BrowseActivity or the CartActivity.
     */
    private final boolean browsing;

    public ItemAdapter(AppCompatActivity activity, List<Item> items, boolean browsing) {
        this.items = items;
        this.activity = activity;
        this.browsing = browsing;
    }

    public int getItemViewType(int position) {
        if (items.isEmpty())
            return EMPTY_VIEW_TYPE;
        else
            return NORMAL_VIEW_TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v;

        if (viewType == NORMAL_VIEW_TYPE)
            v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        else {
            TextView textView = new TextView(parent.getContext());
            textView.setText(R.string.no_items_found);
            textView.setTextSize(18);
            textView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);

            CardView cardView = new CardView(parent.getContext());
            cardView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            cardView.setUseCompatPadding(true);

            cardView.addView(textView, new CardView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));

            textView.setGravity(android.view.Gravity.CENTER);

            v = cardView;
        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // If there are no items do nothing
        if (getItemViewType(position) == EMPTY_VIEW_TYPE)
            return;

        Item item = items.get(position);
        CardView cardView = holder.cardView;

        ImageView imageView = cardView.findViewById(R.id.image);
        imageView.setImageDrawable(ContextCompat.getDrawable(cardView.getContext(), item.getImageID()));

        TextView txtName = cardView.findViewById(R.id.txtName);
        txtName.setText(item.getName());

        TextView txtPrice = cardView.findViewById(R.id.txtPrice);
        txtPrice.setText(String.format(Locale.getDefault(), "%.2f$", item.getPrice()));

        TextView txtDescription = cardView.findViewById(R.id.txtDescription);
        txtDescription.setText(item.getDescription());

        Button btnPurchase = cardView.findViewById(R.id.btnPurchase);
        if (browsing) {
            btnPurchase.setOnClickListener(v -> addToCart(item));
            cardView.setOnClickListener(v -> goToItemActivity(item));
        } else {
            btnPurchase.setText(R.string.purchase);
            btnPurchase.setOnClickListener(v -> purchase(position));

            Button btnCancel = cardView.findViewById(R.id.btnCancel);
            btnCancel.setVisibility(ViewGroup.VISIBLE);
            btnCancel.setOnClickListener(v -> cancel(position));
        }
    }

    @Override
    public int getItemCount() {
        // Returns 1 if no items are found so the onCreateViewHolder method
        // is called and the no items found text is shown.
        return Math.max(items.size(), 1);
    }

    private void goToItemActivity(Item item) {
        Intent intent = new Intent(activity, ItemActivity.class);

        intent.putExtra(ItemActivity.ID, item.getId());
        intent.putExtra(ItemActivity.NAME, item.getName());
        intent.putExtra(ItemActivity.DESCRIPTION, item.getDescription());
        intent.putExtra(ItemActivity.CATEGORY, item.getCategory());
        intent.putExtra(ItemActivity.PRICE, item.getPrice());
        intent.putExtra(ItemActivity.IMAGE, item.getImageID());
        intent.putExtra(ItemActivity.AMOUNT, item.getAmount());

        activity.startActivity(intent);
        activity.finish();
    }

    private void addToCart(Item item) {
        iCartDA cartDA = CartDAFactory.getInstance(activity);
        item.setAmount(1);
        cartDA.addToCart(item);

        Intent intent = new Intent(activity, CartActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void purchase(int itemIndex) {
        iCartDA cartDA = CartDAFactory.getInstance(activity);
        cartDA.checkout(itemIndex);
        ((CartActivity) activity).showCartItems();
    }

    private void cancel(int itemIndex) {
        iCartDA cartDA = CartDAFactory.getInstance(activity);
        cartDA.removeItem(itemIndex);
        ((CartActivity) activity).showCartItems();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
