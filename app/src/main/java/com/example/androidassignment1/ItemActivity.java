package com.example.androidassignment1;

import android.content.Intent;
import android.os.Bundle;
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

    private TextView txtProductName;
    private TextView txtProductDescription;
    private TextView txtProductPrice;
    private ImageView ivImage;

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
        showItemData();
        setupBackButton();
    }

    private void setupViews() {
        txtProductName = findViewById(R.id.txtProductName);
        txtProductDescription = findViewById(R.id.txtDescription);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        ivImage = findViewById(R.id.ivImage);
    }

    private void showItemData() {
        Intent intent = getIntent();

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
}