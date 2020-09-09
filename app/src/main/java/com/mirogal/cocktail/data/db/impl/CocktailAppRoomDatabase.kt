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
import com.mirogal.cocktail.data.db.model.UserDbModel
import com.mirogal.cocktail.data.db.model.cocktail.CocktailInfoDbModel
import com.mirogal.cocktail.data.db.model.cocktail.CocktailInstructionDbModel
import com.mirogal.cocktail.data.db.model.cocktail.CocktailNameDbModel
import com.mirogal.cocktail.util.SingletonHolder

@Database(
        version = 1,
        entities = [
            CocktailInfoDbModel::class,
            CocktailNameDbModel::class,
            CocktailInstructionDbModel::class,
            UserDbModel::class
        ],
        exportSchema = false
)
@TypeConverters(DateConverter::class, StringListToStringConverter::class)
abstract class CocktailAppRoomDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao
    abstract fun userDao(): UserDao

    companion object : SingletonHolder<CocktailAppRoomDatabase, Context>({

//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE ${Table.COCKTAIL_INFO} ADD COLUMN date INTEGER DEFAULT NULL")
//            }
//        }

        Room.databaseBuilder(
                it.applicationContext,
                CocktailAppRoomDatabase::class.java,
                "${CocktailAppRoomDatabase::class.java.simpleName}_test_1"
        )
//                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration() // Migration with destroy ald data
//                .allowMainThreadQueries() // Working in main thread for debugging
                .build()
    })

}