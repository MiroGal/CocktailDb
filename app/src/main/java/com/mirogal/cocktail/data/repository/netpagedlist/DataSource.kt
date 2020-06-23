package com.mirogal.cocktail.data.repository.netpagedlist

import androidx.paging.PositionalDataSource
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.network.WebService
import com.mirogal.cocktail.data.network.entity.ContainerNetEntity
import com.mirogal.cocktail.data.repository.NetDbMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DataSource internal constructor(private val currentQuery: String?) : PositionalDataSource<CocktailDbEntity>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<CocktailDbEntity>) {
        val data = WebService.create().getCocktailContainer(currentQuery)
        data?.enqueue(object : Callback<ContainerNetEntity?> {
            override fun onResponse(call: Call<ContainerNetEntity?>, response: Response<ContainerNetEntity?>) {
                if (response.isSuccessful) {
                    val netEntityList = response.body()!!.cocktailList
                    val dbEntityList: MutableList<CocktailDbEntity> = ArrayList()
                    if (netEntityList != null) { //crash without this check
                        for (i in netEntityList.indices) {
                            dbEntityList.add(NetDbMapper.toDbEntity(netEntityList[i]))
                        }
                    }
                    callback.onResult(dbEntityList, params.requestedStartPosition)
                }
            }

            override fun onFailure(call: Call<ContainerNetEntity?>, t: Throwable) {}
        })
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CocktailDbEntity>) {}

}