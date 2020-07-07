package com.mirogal.cocktail.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity

@Database(entities = [CocktailDbEntity::class], version = 1, exportSchema = false)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun dao(): CocktailDao

    companion object {

        @Volatile
        private var INSTANCE: CocktailDatabase? = null

        fun newInstance(application: Application): CocktailDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        application,
                        CocktailDatabase::class.java,
                        "cocktail_database_test_2"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}