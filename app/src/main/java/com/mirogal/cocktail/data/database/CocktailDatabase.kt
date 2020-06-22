package com.mirogal.cocktail.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity

@Database(entities = [CocktailDbEntity::class], version = 1, exportSchema = false)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao

    companion object {
        private var INSTANCE: CocktailDatabase? = null

        fun getInstance(context: Context): CocktailDatabase? {
            if (INSTANCE == null) {
                synchronized(CocktailDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CocktailDatabase::class.java,
                            "cocktail_database_test_2").build()
                }
            }
            return INSTANCE
        }
    }

}