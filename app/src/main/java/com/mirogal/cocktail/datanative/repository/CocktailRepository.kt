package com.mirogal.cocktail.datanative.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mirogal.cocktail.datanative.db.CocktailDatabase
import com.mirogal.cocktail.datanative.db.model.CocktailDbModel
import com.mirogal.cocktail.datanative.network.model.NetworkStatus
import com.mirogal.cocktail.datanative.network.source.BoundaryCallback
import com.mirogal.cocktail.datanative.network.source.DataSourceFactory

class CocktailRepository(application: Application) {

    private val database = CocktailDatabase.newInstance(application)

    lateinit var saveCocktailListLiveData: LiveData<List<CocktailDbModel>?>
    var loadCocktailListLiveData: LiveData<PagedList<CocktailDbModel?>> = MutableLiveData()
    val searchNameMutableLiveData: MutableLiveData<String?> = MutableLiveData()
    val networkStatusMutableLiveData: MutableLiveData<NetworkStatus.Status> = MutableLiveData()

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
        initLoadCocktailList()
    }

    private fun initSaveCocktailList() {
        saveCocktailListLiveData = database.dao().cocktailListLiveData
    }

    private fun initLoadCocktailList() {
        val pagedListConfig = PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(40)
                .setEnablePlaceholders(false)
                .build()
        val dataSourceFactory = DataSourceFactory()
        val boundaryCallback = BoundaryCallback(this)
        loadCocktailListLiveData = Transformations.switchMap(searchNameMutableLiveData) {
            if (searchNameMutableLiveData.value == null || searchNameMutableLiveData.value!!.isEmpty()) {
                dataSourceFactory.setCurrentQuery(null)
            } else {
                dataSourceFactory.setCurrentQuery(searchNameMutableLiveData.value)
            }
            dataSourceFactory.create()
            LivePagedListBuilder(dataSourceFactory, pagedListConfig)
                    .setBoundaryCallback(boundaryCallback)
                    .build()
        }
    }


    fun getCocktailById(cocktailId: Int): LiveData<CocktailDbModel> {
        return database.dao().getCocktailById(cocktailId)
    }

    fun addCocktailToDb(cocktail: CocktailDbModel?) {
        Thread(Runnable { database.dao().addCocktail(cocktail!!) }).start()
    }

    fun deleteCocktailFromDb(cocktailId: Int) {
        Thread(Runnable { database.dao().deleteCocktail(cocktailId) }).start()
    }

    fun setCocktailStateFavorite(cocktailId: Int, isFavorite: Boolean) {
        Thread(Runnable { database.dao().setFavorite(cocktailId, isFavorite) }).start()
    }

}