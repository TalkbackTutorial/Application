package com.github.talkbacktutorial.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The database for Teach Me Talkback, implemented as a singleton as we only want one instance of
 * the database throughout the application
 * @author Antony Loose
 */
@Database(entities = [ModuleProgression::class], version = 1, exportSchema = false)
abstract class ModuleDatabase: RoomDatabase() {

    abstract fun moduleProgressionDao(): ModuleProgressionDao

    companion object {
        @Volatile
        private var INSTANCE: ModuleDatabase? = null

        fun getDatabase(context: Context): ModuleDatabase {
            val tmpInstance = INSTANCE
            if (tmpInstance != null){
                return tmpInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ModuleDatabase::class.java,
                    "module_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}