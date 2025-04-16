package com.example.androidassignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private SharedPreferences sharedPreferences;

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

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setupViews();
        showItems();
    }

    private void setupViews() {
        rvItems = findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showItems() {
        iItemDA itemDA = ItemDAFactory.getInstance(sharedPreferences);
        List<Item> items = itemDA.getAllItems();

        ItemAdapter adapter = new ItemAdapter(this, items);
        rvItems.setAdapter(adapter);
    }

    public void searchOnClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}