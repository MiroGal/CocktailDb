package com.mirogal.cocktail.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.study.drink.ProposeDrinkService
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.constant.IntentTag
import com.mirogal.cocktail.ui.detail.ingredientlist.IngredientMapper.toIngredientList
import com.mirogal.cocktail.ui.detail.ingredientlist.ListAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : BaseActivity() {

    private var entityId = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val cocktailEntity = intent.getSerializableExtra(IntentTag.COCKTAIL_ENTITY.toString()) as CocktailDbEntity
        entityId = cocktailEntity.id

//        if (cocktailEntity.name!!.isNotEmpty()) {
//            toolbar.title = cocktailEntity.name
//        }

        iv_back.setOnClickListener {
            onBackPressed()
        }

        tv_info_name.text = cocktailEntity.name
        tv_info_alcoholic.text = cocktailEntity.alcoholic
        tv_info_glass.text = cocktailEntity.glass
        tvInstructionBody.text = cocktailEntity.instruction

        rv_ingredient_list.layoutManager = LinearLayoutManager(this)
        val ingredientList = toIngredientList(
                cocktailEntity.ingredientList, cocktailEntity.measureList)
        val listAdapter = ListAdapter(ingredientList, R.layout.item_ingredient)
        rv_ingredient_list.adapter = listAdapter

        Glide.with(this)
                .load(cocktailEntity.imagePath)
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage)
    }

    override fun onDestroy() {
        val intent = Intent(this, ProposeDrinkService::class.java)
        intent.putExtra("KEY", entityId)
        ProposeDrinkService.enqueueWork(this, intent)
        super.onDestroy()
    }

}