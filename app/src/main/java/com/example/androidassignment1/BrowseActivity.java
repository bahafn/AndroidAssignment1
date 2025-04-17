package com.example.androidassignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidassignment1.DataAccess.Item.Item;
import com.example.androidassignment1.DataAccess.Item.ItemDAFactory;
import com.example.androidassignment1.DataAccess.Item.iItemDA;

import java.util.List;

public class BrowseActivity extends AppCompatActivity {
    private RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        showItems();
    }

    private void setupViews() {
        rvItems = findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showItems() {
        iItemDA itemDA = ItemDAFactory.getInstance(this);
        List<Item> items = itemDA.getAllAvailableItems();

        ItemAdapter adapter = new ItemAdapter(this, items, true);
        rvItems.setAdapter(adapter);
    }

    public void searchOnClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void fabCartOnClick(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}