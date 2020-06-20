package com.mirogal.cocktail.ui.savelist

import android.os.Bundle
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity

class SaveListActivity : BaseActivity(), SaveListFragment.OnFragmentActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_list)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, SaveListFragment.newInstance(), SaveListFragment::class.java.simpleName)
                    .commit()
        }

//        val fragment = supportFragmentManager.findFragmentByTag(SaveListFragment::class.java.simpleName) as SaveListFragment
    }


    override fun onFilterSelected() {
        addFilterFragment()
    }


    private fun addFilterFragment() {
        val newFragment = FilterFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment, FilterFragment::class.java.simpleName)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}