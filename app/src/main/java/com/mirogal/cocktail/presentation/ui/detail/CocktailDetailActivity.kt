package com.mirogal.cocktail.presentation.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.presentation.ui.constant.IntentTag
import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.IngredientMapper.toIngredientList
import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.ListAdapter
import kotlinx.android.synthetic.main.activity_cocktail_detail.*
import kotlinx.android.synthetic.main.content_cocktail_detail.*

class CocktailDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_detail)

        val cocktailEntity = intent.getSerializableExtra(IntentTag.COCKTAIL_ENTITY.toString()) as CocktailDbEntity

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        if (cocktailEntity.name.isNotEmpty()) {
            toolbar.title = cocktailEntity.name
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        tvInfoName.text = cocktailEntity.name
        tvInfoAlcoholic.text = cocktailEntity.alcoholic
        tvInfoGlass.text = cocktailEntity.glass
        tvInstructionBody.text = cocktailEntity.instruction

        recyclerView.layoutManager = LinearLayoutManager(this)
        val ingredientList = toIngredientList(
                cocktailEntity.ingredientList, cocktailEntity.measureList)
        val listAdapter = ListAdapter(ingredientList, R.layout.item_ingredient)
        recyclerView.adapter = listAdapter

        Glide.with(this)
                .load(cocktailEntity.imagePath)
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage)
    }

}