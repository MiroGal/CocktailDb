package com.mirogal.cocktail.presentation.ui.searchlist;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirogal.cocktail.R;
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.data.repository.NetworkState;
import com.mirogal.cocktail.presentation.ui.constant.IntentTag;
import com.mirogal.cocktail.presentation.ui.detail.CocktailDetailActivity;
import com.mirogal.cocktail.presentation.ui.util.GridSpaceItemDecoration;
import com.mirogal.cocktail.presentation.viewmodel.searchlist.CocktailSearchListViewModel;

public class CocktailSearchListActivity extends AppCompatActivity
        implements ListAdapter.OnItemClickListener {

    private CocktailSearchListViewModel viewModel;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private GridSpaceItemDecoration itemDecoration;
    private ListAdapter listAdapter;
    private LinearLayout layoutEmpty;
    private LinearLayout layoutStart;

    private SearchView searchView;
    private MenuItem searchMenuItem;
    private String requestQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_search_list);

        viewModel = new ViewModelProvider(this).get(CocktailSearchListViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int listColumn;
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listColumn = 2;
        } else {
            listColumn = 3;
        }

        recyclerView = (RecyclerView) findViewById(R.id.rw_list);
        layoutEmpty = (LinearLayout) findViewById(R.id.layout_empty);
        layoutStart = (LinearLayout) findViewById(R.id.layout_start);

        layoutManager = new GridLayoutManager(this, listColumn);
        recyclerView.setLayoutManager(layoutManager);

        int spaceInPixel = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        itemDecoration = new GridSpaceItemDecoration(listColumn, spaceInPixel, true, 0);
        recyclerView.addItemDecoration(itemDecoration);

        listAdapter = new ListAdapter(this, this, R.layout.item_cocktail);
        viewModel.getCocktailList().observe(this, pagedList -> {
            try {
                listAdapter.submitList(pagedList);
            } catch (Exception e) {}
        });
        recyclerView.setAdapter(listAdapter);

        viewModel.getNetworkStatus().observe(this, new Observer<NetworkState.Status>() {
            @Override
            public void onChanged(NetworkState.Status status) {
                if (status == NetworkState.EMPTY) {
                    showEmpty();
                } else {
                    showData();
                }
            }
        });

        viewModel.getRequestQuery().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                requestQuery = s;
                if (s == null || s.isEmpty()) {
                    showStart();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cocktail_search_list_function, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setIconifiedByDefault(false); // set inner icon
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        if (requestQuery != null) {
            searchView.setQuery(requestQuery, false);
        }
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        viewModel.setRequestQuery(s);
                        return false;
                    }
                }
        );
        return true;
    }

    @Override
    public void onItemClick(CocktailDbEntity cocktail) {
        viewModel.saveCocktail(cocktail);
        openCocktailDetailActivity(cocktail);
    }

    public void openCocktailDetailActivity(CocktailDbEntity cocktail) {
        Intent intent = new Intent(CocktailSearchListActivity.this, CocktailDetailActivity.class);
        intent.putExtra(IntentTag.COCKTAIL_ENTITY.toString(), cocktail);
        startActivity(intent);
    }

    private void showData() {
        if (recyclerView.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.INVISIBLE);
            layoutStart.setVisibility(View.INVISIBLE);
        }
    }

    private void showEmpty() {
        if (layoutEmpty.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.INVISIBLE);
            layoutEmpty.setVisibility(View.VISIBLE);
            layoutStart.setVisibility(View.INVISIBLE);
        }
    }

    private void showStart() {
        if (layoutStart.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.INVISIBLE);
            layoutEmpty.setVisibility(View.INVISIBLE);
            layoutStart.setVisibility(View.VISIBLE);
        }
    }
}
