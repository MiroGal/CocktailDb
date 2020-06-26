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
import com.mirogal.cocktail.service.ProposeDrinkService
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.detail.IngredientMapper.toIngredientList
import kotlinx.android.synthetic.main.activity_drink_detail.*
import kotlinx.android.synthetic.main.content_drink_detail.*
import kotlin.math.abs


class DrinkDetailActivity : BaseActivity() {

    private lateinit var cocktailEntity: CocktailDbEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_detail)

        cocktailEntity = intent.getSerializableExtra(DrinkDetailActivity::class.java.simpleName) as CocktailDbEntity

        setSupportActionBar(toolbar)
        if (cocktailEntity.name!!.isNotEmpty()) {
            supportActionBar!!.title = cocktailEntity.name
        }

        setAppBar()
        setDrinkData()

        btn_toolbar_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setDrinkData() {
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

    private fun setAppBar() {
        // Init state
        toolbar_layout.setExpandedTitleColor(resources.getColor(R.color.txt_toolbar_expanded))
        toolbar_layout.setCollapsedTitleTextColor(resources.getColor(R.color.txt_toolbar_collapsed))
        iv_image.setColorFilter(Color.argb(85, 0, 0, 0))

        // Initial values
        val collapsedWidth = convertDpToPixel(40.0F, context = applicationContext)
        val collapsedHeight = convertDpToPixel(40.0F, context = applicationContext)

        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        val expandedWidth = size.x
        val expandedHeight = fl_image_container.layoutParams.height

        val collapsedMarginLeft = convertDpToPixel(56.0F, context = applicationContext)
        val collapsedMarginBottom = convertDpToPixel(8.0F, context = applicationContext)

        val collapsedMarginLeftToolbar = convertDpToPixel(96.0F, context = applicationContext)

        var lastHeight = expandedHeight
        val layoutParams = iv_image.layoutParams as FrameLayout.LayoutParams
        val layoutParamsToolbar = toolbar.layoutParams as ViewGroup.MarginLayoutParams

        // Declare variables
        var collapsedScaleInverse: Float
        var collapsedScale: Float

        var currentWidth: Float
        var currentHeight: Float

        var currentMarginLeft: Float
        var currentMarginBottom: Float

        var currentMarginLeftToolbar: Float

        app_bar_layout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            collapsedScaleInverse = abs(verticalOffset).toFloat() / appBarLayout!!.totalScrollRange.toFloat()
            collapsedScale = 1.0F - collapsedScaleInverse

            currentWidth = collapsedWidth + ((expandedWidth - collapsedWidth) * collapsedScale)
            currentHeight = collapsedHeight + ((expandedHeight - collapsedHeight) * collapsedScale)

            if (lastHeight != currentHeight.toInt()) {
                lastHeight = currentHeight.toInt()
                // Set image tint color
                iv_image.setColorFilter(Color.argb((85 * collapsedScale).toInt(), 0, 0, 0))
                // Set image margin
                currentMarginLeft = (collapsedMarginLeft * collapsedScaleInverse)
                currentMarginBottom = (collapsedMarginBottom * collapsedScaleInverse)
                layoutParams.setMargins(currentMarginLeft.toInt(), 0, 0, currentMarginBottom.toInt())
                // Set image measure
                iv_image.layoutParams.width = currentWidth.toInt()
                iv_image.layoutParams.height = currentHeight.toInt()
                iv_image.layoutParams = layoutParams
                iv_image.requestLayout()
                // Set toolbar margin
                currentMarginLeftToolbar = (collapsedMarginLeftToolbar * collapsedScaleInverse)
                layoutParamsToolbar.marginStart = currentMarginLeftToolbar.toInt()
                toolbar.layoutParams = layoutParamsToolbar
            }
        })
    }

    private fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    override fun onDestroy() {
        val intent = Intent(this, ProposeDrinkService::class.java)
        intent.putExtra(ProposeDrinkService::class.java.simpleName, cocktailEntity.id)
        ProposeDrinkService.enqueueWork(this, intent)
        super.onDestroy()
    }

}
