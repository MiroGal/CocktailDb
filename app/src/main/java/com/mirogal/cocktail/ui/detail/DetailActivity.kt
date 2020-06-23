package com.mirogal.cocktail.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.receiver.ProposeDrinkService
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.constant.IntentTag
import com.mirogal.cocktail.ui.detail.ingredientlist.IngredientMapper.toIngredientList
import com.mirogal.cocktail.ui.detail.ingredientlist.ListAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlin.math.abs


class DetailActivity : BaseActivity() {

    private var entityId = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val cocktailEntity = intent.getSerializableExtra(IntentTag.COCKTAIL_ENTITY.toString()) as CocktailDbEntity
        entityId = cocktailEntity.id

        setSupportActionBar(toolbar)
        if (cocktailEntity.name!!.isNotEmpty()) {
            supportActionBar!!.title = cocktailEntity.name
        }

        toolbar_layout.setExpandedTitleColor(resources.getColor(R.color.txt_toolbar_expanded))
        toolbar_layout.setCollapsedTitleTextColor(resources.getColor(R.color.txt_toolbar_collapsed))
        iv_image.setColorFilter(Color.argb(85, 0, 0, 0))

        val collapsedWidth = convertDpToPixel(40.0F, context = applicationContext)
        val collapsedHeight = convertDpToPixel(40.0F, context = applicationContext)
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        val expandedWidth = size.x
        val expandedHeight = fl_image_container.layoutParams.height

        val collapsedMarginLeft = convertDpToPixel(52.0F, context = applicationContext)
        val collapsedMarginBottom = convertDpToPixel(4.0F, context = applicationContext)

        val collapsedMarginLeftToolbar = convertDpToPixel(88.0F, context = applicationContext)

        var lastHeight = expandedHeight
        val layoutParams = iv_image.layoutParams as FrameLayout.LayoutParams
        val layoutParamsToolbar = toolbar.layoutParams as ViewGroup.MarginLayoutParams

        app_bar_layout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val collapsedScaleInverse = abs(verticalOffset).toFloat() / appBarLayout!!.totalScrollRange.toFloat()
            val collapsedScale = 1.0F - collapsedScaleInverse

            val currentWidth = collapsedWidth + ((expandedWidth - collapsedWidth) * collapsedScale)
            val currentHeight = collapsedHeight + ((expandedHeight - collapsedHeight) * collapsedScale)

            if (lastHeight != currentHeight.toInt()) {
                lastHeight = currentHeight.toInt()
                iv_image.layoutParams.width = currentWidth.toInt()
                iv_image.layoutParams.height = currentHeight.toInt()

                iv_image.setColorFilter(Color.argb((85 * collapsedScale).toInt(), 0, 0, 0))

                val currentMarginLeft = (collapsedMarginLeft * collapsedScaleInverse)
                val currentMarginBottom = (collapsedMarginBottom * collapsedScaleInverse)
                layoutParams.setMargins(currentMarginLeft.toInt(), 0, 0, currentMarginBottom.toInt())
                iv_image.layoutParams = layoutParams
                iv_image.requestLayout()

                val currentMarginLeftToolbar = (collapsedMarginLeftToolbar * collapsedScaleInverse)
                layoutParamsToolbar.marginStart = currentMarginLeftToolbar.toInt()
                toolbar.layoutParams = layoutParamsToolbar
            }
        })

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
        val listAdapter = ListAdapter(ingredientList)
        rv_ingredient_list.adapter = listAdapter

        Glide.with(this)
                .load(cocktailEntity.imagePath)
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(iv_image)
    }

    override fun onDestroy() {
        val intent = Intent(this, ProposeDrinkService::class.java)
        intent.putExtra("KEY", entityId)
        ProposeDrinkService.enqueueWork(this, intent)
        super.onDestroy()
    }


    private fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

}