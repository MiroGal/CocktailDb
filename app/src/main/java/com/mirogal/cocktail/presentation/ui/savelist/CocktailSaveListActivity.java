package com.mirogal.cocktail.presentation.ui.savelist;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    private CocktailSaveListViewModel viewModel;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private GridSpaceItemDecoration itemDecoration;
    private ListAdapter listAdapter;
    private TextView tvEmpty;

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

        fabSearch = (FloatingActionButton) findViewById(R.id.fab_search);
        fabSearch.setOnClickListener(onFabClickListener);

        int listColumn;
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listColumn = 2;
        } else {
            listColumn = 3;
        }

        recyclerView = (RecyclerView) findViewById(R.id.rw_list);
        tvEmpty = (TextView) findViewById(R.id.tv_empty);

        layoutManager = new GridLayoutManager(this, listColumn);
        recyclerView.setLayoutManager(layoutManager);

        int spaceInPixel = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
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
            } catch (Exception e) {}
        });
        recyclerView.setAdapter(listAdapter);
    }

    View.OnClickListener onFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openCocktailSearchListActivity();
        }
    };

    @Override
    public void onItemClick(CocktailDbEntity cocktail) {
        openCocktailDetailActivity(cocktail);
    }

    @Override
    public void onItemLongClick(int cocktailId) {
        viewModel.deleteCocktail(cocktailId);
    }

    public void openCocktailSearchListActivity() {
        Intent intent = new Intent(CocktailSaveListActivity.this, CocktailSearchListActivity.class);
        startActivity(intent);
    }

    public void openCocktailDetailActivity(CocktailDbEntity cocktail) {
        Intent intent = new Intent(CocktailSaveListActivity.this, CocktailDetailActivity.class);
        intent.putExtra(IntentTag.COCKTAIL_ENTITY.toString(), cocktail);
        startActivity(intent);
    }

    private void showData() {
        if (recyclerView.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private void showEmpty() {
        if (tvEmpty.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.INVISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }
}
