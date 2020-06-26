package com.mirogal.cocktail.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mirogal.cocktail.data.database.CocktailDatabase
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.netpagedlist.BoundaryCallback
import com.mirogal.cocktail.data.repository.netpagedlist.DataSourceFactory

class CocktailRepository private constructor(context: Context) {

    var saveCocktailList: LiveData<List<CocktailDbEntity>>? = null
        private set
    var selectCocktailList: LiveData<PagedList<CocktailDbEntity?>> = MutableLiveData()
        private set
    val networkStatus: MutableLiveData<NetworkState.Status> = MutableLiveData()
    val requestQuery: MutableLiveData<String?> = MutableLiveData()

    private val db = CocktailDatabase.getInstance(context)

    private fun initSaveCocktailList() {
        saveCocktailList = db?.cocktailDao()?.lvCocktailList
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
        Thread(Runnable { db?.cocktailDao()?.insertCocktail(cocktail!!) }).start()
    }

    fun deleteCocktail(cocktailId: Int) {
        Thread(Runnable { db?.cocktailDao()?.deleteCocktail(cocktailId) }).start()
    }

    fun setFavorite(cocktailId: Int, isFavorite: Boolean) {
        Thread(Runnable { db?.cocktailDao()?.setFavorite(cocktailId, isFavorite) }).start()
    }

    init {
        initSaveCocktailList()
        initSelectCocktailList()
    }

    companion object {
        private var INSTANCE: CocktailRepository? = null

        fun getInstance(context: Context): CocktailRepository? {
            if (INSTANCE == null){
                synchronized(CocktailRepository::class){
                    INSTANCE = CocktailRepository(context)
                }
            }
            return INSTANCE
        }
    }

}