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
@Database(entities = [LessonProgression::class], version = 1, exportSchema = false)
abstract class LessonDatabase: RoomDatabase() {

    abstract fun lessonProgressionDao(): LessonProgressionDao

    companion object {
        @Volatile
        private var INSTANCE: LessonDatabase? = null

        fun getDatabase(context: Context): LessonDatabase {
            val tmpInstance = INSTANCE
            if (tmpInstance != null){
                return tmpInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LessonDatabase::class.java,
                    "lesson_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}