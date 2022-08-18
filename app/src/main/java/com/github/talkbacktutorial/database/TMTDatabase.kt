package com.github.talkbacktutorial.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.talkbacktutorial.database.gamemode.GameMode
import com.github.talkbacktutorial.database.gamemode.GameModeDao

/**
 * The database for Teach Me Talkback, implemented as a singleton as we only want one instance of
 * the database throughout the application
 * @author Antony Loose
 */
@Database(entities = [ModuleProgression::class, GameMode::class], version = 2, exportSchema = false)
abstract class TMTDatabase: RoomDatabase() {

    abstract fun moduleProgressionDao(): ModuleProgressionDao
    abstract fun gameModeDao(): GameModeDao

    companion object {
        @Volatile
        private var INSTANCE: TMTDatabase? = null

        fun getDatabase(context: Context): TMTDatabase {
            val tmpInstance = INSTANCE
            if (tmpInstance != null){
                return tmpInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TMTDatabase::class.java,
                    "tmt_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}