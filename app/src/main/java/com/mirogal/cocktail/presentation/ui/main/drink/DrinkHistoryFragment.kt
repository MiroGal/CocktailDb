package com.mirogal.cocktail.presentation.ui.main.drink

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.databinding.FragmentDrinkHistoryBinding
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.detail.DetailActivity
import com.mirogal.cocktail.presentation.ui.main.drink.adapter.DrinkListAdapter
import com.mirogal.cocktail.presentation.ui.util.SpaceItemDecorationWithoutTopMargin
import kotlinx.android.synthetic.main.fragment_drink_history.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*

class DrinkHistoryFragment : BaseFragment<DrinkViewModel, FragmentDrinkHistoryBinding>(),
        DrinkListAdapter.OnItemClickListener,
        DrinkListAdapter.OnItemLongClickListener {

    override val contentLayoutResId = R.layout.fragment_drink_history
    override val viewModel: DrinkViewModel by activityViewModels()

    private lateinit var drinkListAdapter: DrinkListAdapter

    companion object {
        fun newInstance() = DrinkHistoryFragment()
    }

    override fun configureDataBinding(binding: FragmentDrinkHistoryBinding) {
        super.configureDataBinding(binding)
        dataBinding.viewmodel = viewModel
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        rv_drink_history_list.layoutManager = GridLayoutManager(requireContext(), listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = SpaceItemDecorationWithoutTopMargin(listColumn, spaceInPixel, true, 0)
        rv_drink_history_list.addItemDecoration(itemDecoration)

        drinkListAdapter = DrinkListAdapter(requireContext(), this, this)
    }

    override fun configureObserver() {
        viewModel.historyCocktailListLiveData.observe(viewLifecycleOwner, Observer { list ->
            if (list?.isNotEmpty()!!) {
                showData()
            } else {
                showEmpty()
            }
            drinkListAdapter.refreshData(list)
        })
        rv_drink_history_list.adapter = drinkListAdapter
    }


    override fun onItemClick(cocktailId: Int, cocktailName: String?) {
        openDrinkDetailActivity(cocktailId, cocktailName)
    }

    override fun onFavoriteClick(cocktailId: Int, isFavorite: Boolean) {
        viewModel.switchCocktailStateFavorite(cocktailId, isFavorite)
    }

    override fun onItemLongClick(view: View, cocktailModel: CocktailDbModel) {
        showItemMenu(view, cocktailModel)
    }


    private fun showItemMenu(view: View, cocktailModel: CocktailDbModel) {
        val popupMenu = PopupMenu(requireActivity(), view)
        popupMenu.inflate(R.menu.item_drink_history_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_open -> openDrinkDetailActivity(cocktailModel.id, cocktailModel.name)
                R.id.action_shortcut -> addItemShortcut(cocktailModel)
                R.id.action_pin_shortcut -> addItemPinShortcut(cocktailModel)
                R.id.action_add_favorite -> viewModel.setCocktailStateFavorite(cocktailModel.id, true)
                R.id.action_delete -> viewModel.deleteCocktail(cocktailModel.id)
            }
            true
        }
        popupMenu.show()
    }

    private fun addItemShortcut(cocktailModel: CocktailDbModel) {
        // Check system version (must be Android 7.1 or higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            // Download image
            Glide.with(requireActivity())
                    .asBitmap()
                    .load(cocktailModel.imagePath)
//                    .centerCrop() // for square icon
                    .circleCrop() // for round icon
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            // Create ShortcutManager
                            val shortcutManager = requireActivity()
                                    .getSystemService<ShortcutManager>(ShortcutManager::class.java)
                            // Create intent
                            val intent = Intent(requireActivity(), DetailActivity::class.java)
                            intent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
                            intent.putExtra("cocktailId", cocktailModel.id)
                            intent.putExtra("cocktailName", cocktailModel.name)
                            // Create intent stack (for correct work back button)
                            val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(requireActivity())
                            stackBuilder.addParentStack(DetailActivity::class.java)
                            stackBuilder.addNextIntent(intent)
                            // Create ShortcutInfo
                            val shortcutInfo = ShortcutInfo.Builder(requireActivity(),
                                    cocktailModel.id.toString())
                                    .setShortLabel(cocktailModel.name.toString())
                                    .setLongLabel("${cocktailModel.name} (${cocktailModel.alcoholic})")
                                    .setIcon(Icon.createWithBitmap(resource))
//                                    .setIntent(intent) // for single activity
                                    .setIntents(stackBuilder.intents) // for activity stack
                                    .build()
                            // Create Shortcut
                            shortcutManager!!.dynamicShortcuts = listOf(shortcutInfo)

                            Toast.makeText(requireActivity(),
                                    cocktailModel.name.toString() + " "
                                            + getString(R.string.drink_item_menu_toast_shortcut_added),
                                    Toast.LENGTH_SHORT).show()
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
        } else {
            Toast.makeText(requireActivity(), getString(R.string.drink_item_menu_toast_shortcut_not_added), Toast.LENGTH_LONG).show()
        }
    }

    private fun addItemPinShortcut(cocktailModel: CocktailDbModel) {
        // Check system version (must be Android 8.0 or higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Download image
            Glide.with(requireActivity())
                    .asBitmap()
                    .load(cocktailModel.imagePath)
//                    .centerCrop() // for square icon
                    .circleCrop() // for round icon
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            // Create ShortcutManager
                            val shortcutManager = requireActivity()
                                    .getSystemService<ShortcutManager>(ShortcutManager::class.java)
                            // Check supporting pin shortcut
                            if (shortcutManager!!.isRequestPinShortcutSupported) {
                                // Create ShortcutManager
                                val intent = Intent(requireActivity(), DetailActivity::class.java)
                                intent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
                                intent.putExtra("cocktailId", cocktailModel.id)
                                intent.putExtra("cocktailName", cocktailModel.name)
                                // Create intent stack (for correct work back button)
                                val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(requireActivity())
                                stackBuilder.addParentStack(DetailActivity::class.java)
                                stackBuilder.addNextIntent(intent)
                                // Create ShortcutInfo
                                val shortcutInfo = ShortcutInfo.Builder(requireActivity(), cocktailModel.id.toString())
                                        .setShortLabel(cocktailModel.name.toString())
                                        .setLongLabel("${cocktailModel.name} (${cocktailModel.alcoholic})")
                                        .setIcon(Icon.createWithBitmap(resource))
//                                        .setIntent(intent) // for single activity
                                        .setIntents(stackBuilder.intents) // for activity stack
                                        .build()
                                // Create ShortcutResultIntent and CallbackIntent
                                val shortcutResultIntent: Intent = shortcutManager.createShortcutResultIntent(shortcutInfo)
                                val successCallbackIntent = PendingIntent.getBroadcast(requireActivity(),
                                        0, shortcutResultIntent, 0)
                                // Create PinShortcut
                                shortcutManager.requestPinShortcut(shortcutInfo, successCallbackIntent.intentSender)

                                Toast.makeText(requireActivity(),
                                        cocktailModel.name.toString() + " "
                                                + getString(R.string.drink_item_menu_toast_pin_shortcut_added),
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
        } else {
            Toast.makeText(requireActivity(), getString(R.string.drink_item_menu_toast_pin_shortcut_not_added), Toast.LENGTH_LONG).show()
        }
    }

    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(activity, DetailActivity::class.java).apply {
            putExtra("cocktailId", cocktailId)
            putExtra("cocktailName", cocktailName)
        }
        startActivity(intent)
    }

    private fun showData() {
        if (rv_drink_history_list.visibility == View.INVISIBLE) {
            rv_drink_history_list.visibility = View.VISIBLE
            layoutEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.INVISIBLE) {
            rv_drink_history_list.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.VISIBLE
        }
    }

}