package com.mirogal.cocktail.presentation.ui.savelist;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mirogal.cocktail.R;
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.presentation.ui.constant.IntentTag;
import com.mirogal.cocktail.presentation.ui.detail.CocktailDetailActivity;
import com.mirogal.cocktail.presentation.ui.searchlist.CocktailSearchListActivity;
import com.mirogal.cocktail.presentation.ui.util.GridSpaceItemDecoration;
import com.mirogal.cocktail.presentation.viewmodel.savelist.CocktailSaveListViewModel;

import java.util.Objects;

public class CocktailSaveListActivity extends AppCompatActivity
        implements ListAdapter.OnItemClickListener, ListAdapter.OnItemLongClickListener {

    // TODO Dagger integration

    private CocktailSaveListViewModel viewModel;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private GridSpaceItemDecoration itemDecoration;
    private ListAdapter listAdapter;
    private LinearLayout layoutEmpty;

    private FloatingActionButton fabSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_save_list);

        viewModel = new ViewModelProvider(this).get(CocktailSaveListViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.save_list_label);

        fabSearch = findViewById(R.id.fab_search);
        fabSearch.setOnClickListener(onFabClickListener);

        int listColumn;
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listColumn = 2;
        } else {
            listColumn = 3;
        }

        recyclerView = findViewById(R.id.rw_list);
        layoutEmpty = findViewById(R.id.layout_save_list_empty);

        layoutManager = new GridLayoutManager(this, listColumn);
        recyclerView.setLayoutManager(layoutManager);

        int spaceInPixel = getResources().getDimensionPixelSize(R.dimen.padding_horizontal);
        itemDecoration = new GridSpaceItemDecoration(listColumn, spaceInPixel, true, 0);
        recyclerView.addItemDecoration(itemDecoration);

        listAdapter = new ListAdapter(this, this, this,
                R.layout.item_cocktail);
        viewModel.getCocktailList().observe(this, pagedList -> {
            if (pagedList != null && !pagedList.isEmpty()) {
                showData();
            } else {
                showEmpty();
            }
            try {
                listAdapter.submitList(pagedList);
            } catch (Exception ignored) {}
        });
        recyclerView.setAdapter(listAdapter);
    }

    private final View.OnClickListener onFabClickListener = view -> openCocktailSearchListActivity();

    @Override
    public void onItemClick(CocktailDbEntity cocktail) {
        openCocktailDetailActivity(cocktail);
    }

    @Override
    public void onItemLongClick(int cocktailId) {
        viewModel.deleteCocktail(cocktailId);
    }

    private void openCocktailSearchListActivity() {
        Intent intent = new Intent(CocktailSaveListActivity.this, CocktailSearchListActivity.class);
        startActivity(intent);
    }

    private void openCocktailDetailActivity(CocktailDbEntity cocktail) {
        Intent intent = new Intent(CocktailSaveListActivity.this, CocktailDetailActivity.class);
        intent.putExtra(IntentTag.COCKTAIL_ENTITY.toString(), cocktail);
        startActivity(intent);
    }

    private void showData() {
        if (recyclerView.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private void showEmpty() {
        if (layoutEmpty.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.INVISIBLE);
            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }
}
