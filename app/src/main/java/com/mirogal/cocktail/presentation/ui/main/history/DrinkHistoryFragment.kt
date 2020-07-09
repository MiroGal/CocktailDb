package com.mirogal.cocktail.presentation.ui.main.history

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
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.detail.DrinkDetailActivity
import com.mirogal.cocktail.presentation.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_drink_history.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*


class DrinkHistoryFragment : BaseFragment<HistoryViewModel>(), ListAdapter.OnItemClickListener,
        ListAdapter.OnItemLongClickListener {

    override val contentLayoutResId = R.layout.fragment_drink_history
    override val viewModel: HistoryViewModel by activityViewModels()

    private lateinit var listAdapter: ListAdapter

    companion object {
        fun newInstance() = DrinkHistoryFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        setList()
    }

    private fun setList() {
        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        rv_drink_history_list.layoutManager = GridLayoutManager(requireContext(), listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = SpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        rv_drink_history_list.addItemDecoration(itemDecoration)

        listAdapter = ListAdapter(requireContext(), this, this)
        viewModel.historyCocktailListLiveData.observe(viewLifecycleOwner, Observer { list ->
            if (list?.isNotEmpty()!!) {
                showData()
            } else {
                showEmpty()
            }
            listAdapter.refreshData(list)
        })
        rv_drink_history_list.adapter = listAdapter
    }


    override fun onItemClick(cocktailId: Int, cocktailName: String?) {
        openDrinkDetailActivity(cocktailId, cocktailName)
    }

    override fun onFavoriteClick(cocktailId: Int, isFavorite: Boolean) {
        viewModel.switchCocktailStateFavorite(cocktailId, isFavorite)
    }

    override fun onItemLongClick(view: View, cocktailEntity: CocktailDbEntity) {
        createAndOpenItemMenu(view, cocktailEntity)
    }


    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(activity, DrinkDetailActivity::class.java)
        intent.putExtra("cocktailId", cocktailId)
        intent.putExtra("cocktailName", cocktailName)
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

    private fun createAndOpenItemMenu(view: View, cocktailEntity: CocktailDbEntity) {
        val popupMenu = PopupMenu(requireActivity(), view)
        popupMenu.inflate(R.menu.drink_history_item_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_open -> openDrinkDetailActivity(cocktailEntity.id, cocktailEntity.name)
                R.id.action_shortcut -> addItemShortcut(cocktailEntity)
                R.id.action_pin_shortcut -> addItemPinShortcut(cocktailEntity)
                R.id.action_favorite -> viewModel.switchCocktailStateFavorite(cocktailEntity.id, cocktailEntity.isFavorite)
                R.id.action_delete -> viewModel.deleteCocktail(cocktailEntity.id)
            }
            true
        }
        popupMenu.show()
    }

    private fun addItemShortcut(cocktailEntity: CocktailDbEntity) {
        // Check system version (must be Android 7.1 or higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            // Download image
            Glide.with(requireActivity())
                    .asBitmap()
                    .load(cocktailEntity.imagePath)
//                    .centerCrop() // for square icon
                    .circleCrop() // for round icon
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            // Create ShortcutManager
                            val shortcutManager = requireActivity()
                                    .getSystemService<ShortcutManager>(ShortcutManager::class.java)
                            // Create intent
                            val intent = Intent(requireActivity(), DrinkDetailActivity::class.java)
                            intent.action = Intent.ACTION_VIEW
                            intent.putExtra("cocktailId", cocktailEntity.id)
                            intent.putExtra("cocktailName", cocktailEntity.name)
                            // Create intent stack (for correct work back button)
                            val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(requireActivity())
                            stackBuilder.addParentStack(DrinkDetailActivity::class.java)
                            stackBuilder.addNextIntent(intent)
                            // Create ShortcutInfo
                            val shortcutInfo = ShortcutInfo.Builder(requireActivity(),
                                    cocktailEntity.id.toString())
                                    .setShortLabel(cocktailEntity.name.toString())
                                    .setLongLabel("${cocktailEntity.name} (${cocktailEntity.alcoholic})")
                                    .setIcon(Icon.createWithBitmap(resource))
//                                    .setIntent(intent) // for single activity
                                    .setIntents(stackBuilder.intents) // for activity stack
                                    .build()
                            // Create Shortcut
                            shortcutManager!!.dynamicShortcuts = listOf(shortcutInfo)

                            Toast.makeText(requireActivity(),
                                    cocktailEntity.name.toString() + " "
                                            + getString(R.string.drink_history_toast_shortcut_added),
                                    Toast.LENGTH_SHORT).show()
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
        } else {
            Toast.makeText(requireActivity(), getString(R.string.drink_history_toast_shortcut_not_added), Toast.LENGTH_LONG).show()
        }
    }

    private fun addItemPinShortcut(cocktailEntity: CocktailDbEntity) {
        // Check system version (must be Android 8.0 or higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Download image
            Glide.with(requireActivity())
                    .asBitmap()
                    .load(cocktailEntity.imagePath)
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
                                val intent = Intent(requireActivity(), DrinkDetailActivity::class.java)
                                intent.action = Intent.ACTION_VIEW
                                intent.putExtra("cocktailId", cocktailEntity.id)
                                intent.putExtra("cocktailName", cocktailEntity.name)
                                // Create intent stack (for correct work back button)
                                val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(requireActivity())
                                stackBuilder.addParentStack(DrinkDetailActivity::class.java)
                                stackBuilder.addNextIntent(intent)
                                // Create ShortcutInfo
                                val shortcutInfo = ShortcutInfo.Builder(requireActivity(), cocktailEntity.id.toString())
                                        .setShortLabel(cocktailEntity.name.toString())
                                        .setLongLabel("${cocktailEntity.name} (${cocktailEntity.alcoholic})")
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
                                        cocktailEntity.name.toString() + " "
                                                + getString(R.string.drink_history_toast_pin_shortcut_added),
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
        } else {
            Toast.makeText(requireActivity(), getString(R.string.drink_history_toast_pin_shortcut_not_added), Toast.LENGTH_LONG).show()
        }
    }

}