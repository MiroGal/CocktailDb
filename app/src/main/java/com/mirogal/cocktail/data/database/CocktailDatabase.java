package com.mirogal.cocktail.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;

@Database(entities = {CocktailDbEntity.class}, version = 1, exportSchema = false)
public abstract class CocktailDatabase extends RoomDatabase {

    private static final String DB_NAME = "cocktail_database_test";
    private static CocktailDatabase INSTANCE_DB;

    public static CocktailDatabase getInstance(final Context context) {
        if (INSTANCE_DB == null) {
            synchronized (CocktailDatabase.class) {
                if (INSTANCE_DB == null) {
                    INSTANCE_DB = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CocktailDatabase.class,
                            DB_NAME).build();
                }
            }
        }
        return INSTANCE_DB;
    }

    public abstract CocktailDao getDao();
}
