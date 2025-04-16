package com.example.androidassignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ItemActivity extends AppCompatActivity {
    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRICE = "PRICE";
    public static final String IMAGE = "IMAGE";
    public static final String AMOUNT = "AMOUNT";

    private TextView txtProductName;
    private TextView txtProductDescription;
    private TextView txtProductPrice;
    private ImageView ivImage;
    private TextView txtAmount;

    /** The amount of items available. */
    private int amount;
    /** The amount the user wants to add to cart. */
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
        txtProductPrice = findViewById(R.id.txtProductPrice);
        ivImage = findViewById(R.id.ivImage);
        txtAmount = findViewById(R.id.txtAmount);
    }

    private void getItemData() {
        Intent intent = getIntent();

        amount = intent.getIntExtra(AMOUNT, 0);

        txtProductName.setText(intent.getStringExtra(NAME));
        txtProductDescription.setText(intent.getStringExtra(DESCRIPTION));
        ivImage.setImageDrawable(ContextCompat.getDrawable(this, intent.getIntExtra(IMAGE, 0)));

        float price = intent.getFloatExtra(PRICE, 0);
        String priceString = String.format(Locale.getDefault(), "%.1f$", price);
        txtProductPrice.setText(priceString);
    }

    /** Changes the behaviour of the back button. */
    private void setupBackButton() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ItemActivity.this, BrowseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void btnIncreaseOnClick(View view) {
        if (orderedAmount == amount)
            return;

        orderedAmount++;
        txtAmount.setText(String.valueOf(orderedAmount));
    }

    public void btnDecreaseOnClick(View view) {
        if (orderedAmount == 0)
            return;

        orderedAmount--;
        txtAmount.setText(String.valueOf(orderedAmount));
    }
}