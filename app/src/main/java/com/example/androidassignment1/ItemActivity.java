package com.example.androidassignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidassignment1.DataAccess.Cart.CartDAFactory;
import com.example.androidassignment1.DataAccess.Item.Item;

import java.util.Locale;

public class ItemActivity extends AppCompatActivity {
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String CATEGORY = "CATEGORY";
    public static final String PRICE = "PRICE";
    public static final String IMAGE = "IMAGE";
    public static final String AMOUNT = "AMOUNT";

    private TextView txtProductName;
    private TextView txtProductDescription;
    private TextView txtAvailableAmount;
    private TextView txtProductPrice;
    private ImageView ivImage;
    private TextView txtAmount;
    private Button btnPurchase;
    private Button btnIncrease;
    private Button btnDecrease;

    /**
     * An Item Object to save to cart if the user clicks add to cart.
     */
    private Item item;
    /**
     * The amount of items available.
     */
    private int amount;
    /**
     * The amount the user wants to add to cart.
     */
    private int orderedAmount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        getItemData();
        setupBackButton();
    }

    private void setupViews() {
        txtProductName = findViewById(R.id.txtProductName);
        txtProductDescription = findViewById(R.id.txtDescription);
        txtAvailableAmount = findViewById(R.id.txtAvailableAmount);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        ivImage = findViewById(R.id.ivImage);
        txtAmount = findViewById(R.id.txtAmount);
        btnPurchase = findViewById(R.id.btnPurchase);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
    }

    private void getItemData() {
        Intent intent = getIntent();

        item = new Item(intent.getIntExtra(ID, 0),
                intent.getStringExtra(NAME),
                intent.getStringExtra(DESCRIPTION),
                intent.getStringExtra(CATEGORY),
                intent.getIntExtra(IMAGE, R.drawable.no_image),
                intent.getFloatExtra(PRICE, 0),
                intent.getIntExtra(AMOUNT, 0));

        amount = item.getAmount();

        txtProductName.setText(item.getName());
        txtProductDescription.setText(item.getDescription());
        txtAvailableAmount.setText(getString(R.string.in_stock, item.getAmount()));
        ivImage.setImageDrawable(ContextCompat.getDrawable(this, item.getImageID()));

        if (amount <= 0) {
            btnPurchase.setEnabled(false);
            btnIncrease.setEnabled(false);
            btnDecrease.setEnabled(false);

            btnPurchase.setBackgroundColor(getColor(R.color.grey));
            btnIncrease.setBackgroundColor(getColor(R.color.grey));
            btnDecrease.setBackgroundColor(getColor(R.color.grey));

            txtAmount.setText(String.valueOf(0));
        }

        float price = item.getPrice();
        String priceString = String.format(Locale.getDefault(), "%.2f$", price);
        txtProductPrice.setText(priceString);
    }

    /**
     * Changes the behaviour of the back button.
     */
    private void setupBackButton() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                back();
            }
        });
    }

    public void ivBackOnClick(View view) {
        back();
    }

    private void back() {
        Intent intent = new Intent(ItemActivity.this, BrowseActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnIncreaseOnClick(View view) {
        if (orderedAmount >= amount) return;

        orderedAmount++;
        txtAmount.setText(String.valueOf(orderedAmount));
    }

    public void btnDecreaseOnClick(View view) {
        if (orderedAmount <= 1) return;

        orderedAmount--;
        txtAmount.setText(String.valueOf(orderedAmount));
    }

    public void btnAddToCartOnClick(View view) {
        item.setAmount(orderedAmount);
        CartDAFactory.getInstance(this).addToCart(item);

        // Go to cart activity
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
        finish();
    }
}