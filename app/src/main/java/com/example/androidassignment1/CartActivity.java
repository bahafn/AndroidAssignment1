package com.example.androidassignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidassignment1.DataAccess.Cart.Cart;
import com.example.androidassignment1.DataAccess.Cart.CartDAFactory;
import com.example.androidassignment1.DataAccess.Cart.InsufficientStockException;
import com.example.androidassignment1.DataAccess.Cart.iCartDA;

import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCartItems;
    private TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        showCartItems();
        setupBackButton();
    }

    private void setupViews() {
        rvCartItems = findViewById(R.id.rvCartItems);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
    }

    public void showCartItems() {
        iCartDA cartDA = CartDAFactory.getInstance(this);
        Cart cart = cartDA.getCart();

        ItemAdapter adapter = new ItemAdapter(this, cart.getItems(), false);
        rvCartItems.setAdapter(adapter);

        tvTotalPrice.setText(String.format(Locale.getDefault(), "%.2f$", cart.getTotalPrice()));
    }

    public void ivBackOnClick(View view) {
        back();
    }

    public void btnCheckoutOnClick(View view) {
        iCartDA cartDA = CartDAFactory.getInstance(this);

        try {
            cartDA.checkout();
            back();
        } catch (InsufficientStockException ise) {
            Toast.makeText(this, ise.getMessage(), Toast.LENGTH_LONG).show();
            showCartItems();
        }
    }

    private void setupBackButton() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                back();
            }
        });
    }

    /** Goes to BrowseActivity. */
    private void back() {
        Intent intent = new Intent(this, BrowseActivity.class);
        startActivity(intent);
        finish();
    }
}