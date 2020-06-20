package com.mirogal.cocktail.ui.savelist

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.receiver.BatteryChangeReceiver
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.constant.IntentTag
import com.mirogal.cocktail.ui.detail.DetailActivity
import com.mirogal.cocktail.ui.searchlist.SearchListActivity
import com.mirogal.cocktail.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.content_save_list.*
import kotlinx.android.synthetic.main.fragment_save_list.*
import kotlinx.android.synthetic.main.layout_battery_indicator.*
import kotlinx.android.synthetic.main.layout_save_list_empty.*


class SaveListFragment : BaseFragment(), ListAdapter.OnItemClickListener,
        ListAdapter.OnItemLongClickListener, BatteryChangeReceiver.OnBatteryChangeListener {

    override val contentLayoutResId = R.layout.fragment_save_list

    private var listener: OnFragmentActionListener? = null

    private lateinit var viewModel: ViewModel
    private lateinit var proposeDrinkReceiver: BroadcastReceiver
    private val batteryChangeReceiver = BatteryChangeReceiver()

    companion object {
        fun newInstance() = SaveListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.save_list_label)
        setHasOptionsMenu(true)

        fab_search.setOnClickListener(onClickListener)
        btn_battery_indicator_close.setOnClickListener(onClickListener)

        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        rv_save_list.layoutManager = GridLayoutManager(requireContext(), listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = SpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        rv_save_list.addItemDecoration(itemDecoration)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        val listAdapter = ListAdapter(requireContext(), this, this)
        viewModel.cocktailList.observe(viewLifecycleOwner, Observer { pagedList: PagedList<CocktailDbEntity> ->
            if (!pagedList.isEmpty()) {
                showData()
            } else {
                showEmpty()
            }
            try {
                listAdapter.submitList(pagedList)
            } catch (ignored: Exception) {
            }
        })
        rv_save_list.adapter = listAdapter

        proposeDrinkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                showProposeDrink(intent!!.getIntExtra("KEY", 20))
            }
        }

        batteryChangeReceiver.setBatteryChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_list_function, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> { listener?.onFilterSelected()
                return true }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        requireContext().registerReceiver(proposeDrinkReceiver, IntentFilter("ACTION_SNACKBAR"))
        requireContext().registerReceiver(batteryChangeReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        IntentFilter().apply {
            addAction("android.intent.action.BATTERY_LOW")
            addAction("android.intent.action.BATTERY_OK")
        }
    }

    override fun onStop() {
        requireContext().unregisterReceiver(proposeDrinkReceiver)
        requireContext().unregisterReceiver(batteryChangeReceiver)
        super.onStop()
    }


    private val onClickListener = View.OnClickListener { view ->
        when (view) {
            fab_search -> openSearchListActivity()
            btn_battery_indicator_close -> layout_charge_indicator.visibility = View.INVISIBLE
        }
    }

    override fun onItemClick(cocktail: CocktailDbEntity?) {
        openDetailActivity(cocktail!!)
    }

    override fun onItemLongClick(cocktailId: Int) {
        viewModel.deleteCocktail(cocktailId)
    }

    override fun onBatteryChange(level: Int, state: Int) {
        showChargeLevel(level.toString())
        showChargeState(state)
    }


    private fun openSearchListActivity() {
        val intent = Intent(activity, SearchListActivity::class.java)
        startActivity(intent)
    }

    private fun openDetailActivity(cocktail: CocktailDbEntity) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(IntentTag.COCKTAIL_ENTITY.toString(), cocktail)
        startActivity(intent)
    }

    private fun showData() {
        if (rv_save_list.visibility == View.INVISIBLE) {
            rv_save_list.visibility = View.VISIBLE
            layoutEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.INVISIBLE) {
            rv_save_list.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showChargeLevel(level: String) {
        if (layout_charge_indicator.visibility == View.VISIBLE) {
            tv_charge.text = "$level %"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showChargeState(state: Int) {
        if (layout_charge_indicator.visibility == View.VISIBLE) {
            when (state) {
                1 -> {
                    tv_charge.setTextColor(ContextCompat.getColor(requireContext(), R.color.battery_status_charging))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_charging)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(requireContext(), R.color.battery_status_charging))
                }
                2 -> {
                    tv_charge.setTextColor(ContextCompat.getColor(requireContext(), R.color.battery_status_ok))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_full)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(requireContext(), R.color.battery_status_ok))
                }
                3 -> {
                    tv_charge.setTextColor(ContextCompat.getColor(requireContext(), R.color.battery_status_low))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_alert)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(requireContext(), R.color.battery_status_low))
                }
            }
        }
    }

    private fun showProposeDrink(id: Int) {
        var entity: CocktailDbEntity? = null
        viewModel.cocktailList.observe(this, Observer { pagedList: PagedList<CocktailDbEntity> ->
            if (!pagedList.isEmpty()) {
                if (pagedList.size > 1) {
                    entity = pagedList[0]!!
                    if (entity!!.id == id) {
                        entity = pagedList[1]!!
                    }
                }
            }
        })
        if (entity != null) {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    "Переглянути " + entity!!.name, Snackbar.LENGTH_LONG)
                    .setAction("Переглянути") {
                        openDetailActivity(entity!!)
                    }.show()
        }
    }

    interface OnFragmentActionListener {
        fun onFilterSelected()
    }

}