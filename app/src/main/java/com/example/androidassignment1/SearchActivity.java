package com.example.androidassignment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidassignment1.DataAccess.Item.ItemDAFactory;
import com.example.androidassignment1.DataAccess.Item.SortBy;
import com.example.androidassignment1.DataAccess.Item.iItemDA;

import java.util.List;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class SearchActivity extends AppCompatActivity {
    private EditText edtSearch;
    private Spinner spnCategory;
    private RadioGroup rgSort;
    private Switch swtShowUnavailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        setupAnimation();
        setupBackButton();
    }

    private void setupViews() {
        edtSearch = findViewById(R.id.edtSearch);
        spnCategory = findViewById(R.id.spnCategory);
        rgSort = findViewById(R.id.rgSort);
        swtShowUnavailable = findViewById(R.id.swtShowUnavailable);

        // Add categories to spnCategory
        iItemDA itemDA = ItemDAFactory.getInstance(this);
        List<String> categories = itemDA.getCategories();
        categories.add(0, "Any");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        spnCategory.setAdapter(adapter);
    }

    private void setupAnimation() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.search_anim);
        ConstraintLayout main = findViewById(R.id.search_main);

        main.setAnimation(anim);
    }

    public void btnSearchOnClick(View view) {
        Intent intent = new Intent();

        String name = edtSearch.getText().toString();
        intent.putExtra(BrowseActivity.NAME, name);

        String category = spnCategory.getSelectedItem().toString();
        intent.putExtra(BrowseActivity.CATEGORY, category);

        boolean priceIncreasing = rgSort.getCheckedRadioButtonId() == R.id.rbIncreasing;
        boolean priceDecreasing = rgSort.getCheckedRadioButtonId() == R.id.rbDecreasing;
        SortBy sortBy = priceIncreasing ? SortBy.PRICE_INCREASING : priceDecreasing ? SortBy.PRICE_DECREASING : SortBy.DEFAULT;
        intent.putExtra(BrowseActivity.SORT_BY, sortBy.ordinal());

        boolean showUnavailable = swtShowUnavailable.isChecked();
        intent.putExtra(BrowseActivity.SHOW_UNAVAILABLE, showUnavailable);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void ivBackOnClick(View view) {
        back();
    }

    private void setupBackButton() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                back();
            }
        });
    }

    private void back() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
