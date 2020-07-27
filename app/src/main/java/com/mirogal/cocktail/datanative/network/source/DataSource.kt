package com.mirogal.cocktail.datanative.network.source

import androidx.paging.PositionalDataSource
import com.mirogal.cocktail.datanative.db.model.CocktailDbModel
import com.mirogal.cocktail.datanative.network.WebService
import com.mirogal.cocktail.datanative.network.model.ContainerNetModel
import com.mirogal.cocktail.datanative.repository.mapper.NetDbMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DataSource internal constructor(private val currentQuery: String?) : PositionalDataSource<CocktailDbModel>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<CocktailDbModel>) {
        val data = WebService.create().loadCocktailList(currentQuery)
        data?.enqueue(object : Callback<ContainerNetModel?> {
            override fun onResponse(call: Call<ContainerNetModel?>, response: Response<ContainerNetModel?>) {
                if (response.isSuccessful) {
                    val netEntityList = response.body()!!.cocktailList
                    val dbModelList: MutableList<CocktailDbModel> = ArrayList()
                    if (netEntityList != null) { //crash without this check
                        for (i in netEntityList.indices) {
                            dbModelList.add(NetDbMapper.toDbEntity(netEntityList[i]))
                        }
                    }
                    callback.onResult(dbModelList, params.requestedStartPosition)
                }
            }

            override fun onFailure(call: Call<ContainerNetModel?>, t: Throwable) {}
        })
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CocktailDbModel>) {}

}