package com.mirogal.cocktail.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.service.ProposeDrinkService
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import com.mirogal.cocktail.presentation.ui.detail.adapter.DetailListAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_content.*
import kotlin.math.abs

class DetailActivity : BaseActivity<DetailViewModel>() {

    override val contentLayoutResId = R.layout.activity_detail
    override fun getViewModelClass() = DetailViewModel::class

    private var cocktailId: Long = 0
    private var cocktailName: String? = ""

    override fun configureView(savedInstanceState: Bundle?) {
        super.configureView(savedInstanceState)

        cocktailId = intent.getLongExtra("cocktailId", 0)
        cocktailName = intent.getStringExtra("cocktailName") ?: ""

        setSupportActionBar(toolbar)
        if (cocktailName!!.isNotEmpty()) {
            supportActionBar!!.title = cocktailName
        }

        setScrollAppBar()

        btn_toolbar_back.setOnClickListener { onBackPressed() }
    }

    override fun configureObserver() {
        super.configureObserver()

        viewModel.cocktailIdLiveData.value = cocktailId
        viewModel.cocktailLiveData.observe(this, Observer {
            if (it != null) {
                tv_info_name.text = it.names.default
                tv_info_alcoholic.text = it.alcoholType.key
                tv_info_glass.text = it.glass.key
                tvInstructionBody.text = it.instructions.default

                rv_ingredient_list.layoutManager = LinearLayoutManager(this)
                val ingredientsWithMeasures = it.ingredientsWithMeasures.toList()
                val listAdapter = DetailListAdapter(ingredientsWithMeasures)
                rv_ingredient_list.adapter = listAdapter

                Glide.with(this)
                        .load(it.image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_placeholder_drink)
                        .error(R.drawable.ic_placeholder_error)
                        .into(iv_image)
            }
        })
    }

    private fun setScrollAppBar() {
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
        intent.putExtra("cocktailId", cocktailId)
        ProposeDrinkService.enqueueWork(this, intent)
        super.onDestroy()
    }

}