package com.mirogal.cocktail.data.db.impl

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mirogal.cocktail.data.db.impl.dao.CocktailDao
import com.mirogal.cocktail.data.db.impl.dao.UserDao
import com.mirogal.cocktail.data.db.impl.typeconverter.DateConverter
import com.mirogal.cocktail.data.db.impl.typeconverter.StringListToStringConverter
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.db.model.UserDbModel
import com.mirogal.cocktail.util.SingletonHolder

@Database(
        version = 1,
        entities = [
            CocktailDbModel::class,
            UserDbModel::class
        ],
        exportSchema = false
)
@TypeConverters(DateConverter::class, StringListToStringConverter::class)
abstract class CocktailAppRoomDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao
    abstract fun userDao(): UserDao

    companion object : SingletonHolder<CocktailAppRoomDatabase, Context>({
        Room
                .databaseBuilder(
                        it.applicationContext,
                        CocktailAppRoomDatabase::class.java,
                        CocktailAppRoomDatabase::class.java.name
                )
                .fallbackToDestructiveMigration()
                //.fallbackToDestructiveMigration()
                .build()
    })

}