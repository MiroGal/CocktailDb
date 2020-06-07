package com.mirogal.cocktail.presentation.ui.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mirogal.cocktail.R;
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.presentation.ui.constant.IntentTag;
import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.IngredientMapper;
import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.ListAdapter;
import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.entity.IngredientEntity;

import java.util.List;
import java.util.Objects;

public class CocktailDetailActivity extends AppCompatActivity {

    // TODO Dagger integration

    private CocktailDbEntity cocktailEntity;

    private ImageView ivImage;
    private TextView tvInfoName;
    private TextView tvInfoAlcoholic;
    private TextView tvInfoGlass;
    private TextView tvInstructionBody;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cocktailEntity = (CocktailDbEntity) getIntent().getSerializableExtra(
                IntentTag.COCKTAIL_ENTITY.toString());

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        if (cocktailEntity != null
                && cocktailEntity.getName() != null
                && !cocktailEntity.getName().isEmpty()) {
            toolbar.setTitle(cocktailEntity.getName());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ivImage = findViewById(R.id.iv_image);
        tvInfoName = findViewById(R.id.tv_info_name);
        tvInfoAlcoholic = findViewById(R.id.tv_info_alcoholic);
        tvInfoGlass = findViewById(R.id.tv_info_glass);
        tvInstructionBody = findViewById(R.id.tv_instruction_body);

        tvInfoName.setText(cocktailEntity.getName());
        tvInfoAlcoholic.setText(cocktailEntity.getAlcoholic());
        tvInfoGlass.setText(cocktailEntity.getGlass());
        tvInstructionBody.setText(cocktailEntity.getInstruction());

        recyclerView = findViewById(R.id.rw_list);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<IngredientEntity> ingredientList = IngredientMapper.toIngredientList(
                cocktailEntity.getIngredientList(), cocktailEntity.getMeasureList());
        listAdapter = new ListAdapter(ingredientList, R.layout.item_ingridient);
        recyclerView.setAdapter(listAdapter);

        Glide.with(this)
                .load(cocktailEntity.getImagePath())
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage);
    }
}
