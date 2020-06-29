package com.mirogal.cocktail.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mirogal.cocktail.data.database.CocktailDatabase
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.netpagedlist.BoundaryCallback
import com.mirogal.cocktail.data.repository.netpagedlist.DataSourceFactory

class CocktailRepository(application: Application) {

    private val database = CocktailDatabase.newInstance(application)

    var saveCocktailList: LiveData<List<CocktailDbEntity>>? = null
        private set
    var selectCocktailList: LiveData<PagedList<CocktailDbEntity?>> = MutableLiveData()
        private set
    val networkStatus: MutableLiveData<NetworkState.Status> = MutableLiveData()
    val requestQuery: MutableLiveData<String?> = MutableLiveData()

    companion object {

        @Volatile
        private var INSTANCE: CocktailRepository? = null

        fun newInstance(application: Application): CocktailRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = CocktailRepository(application)
                INSTANCE = instance
                return instance
            }
        }
    }

    init {
        initSaveCocktailList()
        initSelectCocktailList()
    }

    private fun initSaveCocktailList() {
        saveCocktailList = database.cocktailDao().lvCocktailList
    }

    private fun initSelectCocktailList() {
        val pagedListConfig = PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(40)
                .setEnablePlaceholders(false)
                .build()
        val dataSourceFactory = DataSourceFactory()
        val boundaryCallback = BoundaryCallback(this)
        selectCocktailList = Transformations.switchMap(requestQuery) {
            if (requestQuery.value == null || requestQuery.value!!.isEmpty()) {
                dataSourceFactory.setCurrentQuery(null)
            } else {
                dataSourceFactory.setCurrentQuery(requestQuery.value)
            }
            dataSourceFactory.create()
            LivePagedListBuilder(dataSourceFactory, pagedListConfig)
                    .setBoundaryCallback(boundaryCallback)
                    .build()
        }
    }


    fun saveCocktail(cocktail: CocktailDbEntity?) {
        Thread(Runnable { database.cocktailDao().insertCocktail(cocktail!!) }).start()
    }

    fun deleteCocktail(cocktailId: Int) {
        Thread(Runnable { database.cocktailDao().deleteCocktail(cocktailId) }).start()
    }

    fun setFavorite(cocktailId: Int, isFavorite: Boolean) {
        Thread(Runnable { database.cocktailDao().setFavorite(cocktailId, isFavorite) }).start()
    }

}