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
import com.example.androidassignment1.DataAccess.Item.SortBy;
import com.example.androidassignment1.DataAccess.Item.ItemDAFactory;
import com.example.androidassignment1.DataAccess.Item.iItemDA;

import java.util.List;

public class BrowseActivity extends AppCompatActivity {
    public static final int SEARCH_REQUEST_CODE = 1;
    public static final String NAME = "NAME";
    public static final String CATEGORY = "CATEGORY";
    public static final String SORT_BY = "SORT_BY";
    public static final String SHOW_UNAVAILABLE = "SHOW_UNAVAILABLE";

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
        showAllItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_REQUEST_CODE && resultCode == RESULT_OK)
            handleSearch(data);
    }

    private void handleSearch(Intent data) {
        String name = data.getStringExtra(NAME);
        String category = data.getStringExtra(CATEGORY);
        SortBy sortBy = SortBy.values()[data.getIntExtra(SORT_BY, SortBy.DEFAULT.ordinal())];
        boolean showUnavailable = data.getBooleanExtra(SHOW_UNAVAILABLE, false);

        iItemDA itemDA = ItemDAFactory.getInstance(this);
        List<Item> items = itemDA.searchItems(name, category, sortBy, showUnavailable);
        showItems(items);
    }

    private void setupViews() {
        rvItems = findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showAllItems() {
        iItemDA itemDA = ItemDAFactory.getInstance(this);
        List<Item> items = itemDA.getAllAvailableItems();
        showItems(items);
    }

    private void showItems(List<Item> items) {
        ItemAdapter adapter = new ItemAdapter(this, items, true);
        rvItems.setAdapter(adapter);
    }

    public void searchOnClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, SEARCH_REQUEST_CODE);
    }

    public void fabCartOnClick(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
