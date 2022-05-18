package com.github.talkbacktutorial.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The data access object for accessing the progression of lessons
 * @author Antony Loose
 */
@Dao
interface LessonProgressionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLessonProgression(lessonProgression: LessonProgression)

    @Query("SELECT * FROM lesson_table WHERE id-1 == :lessonNum")
    fun getLessonProgression(lessonNum: Int): LiveData<LessonProgression>

    @Query("SELECT * FROM lesson_table ORDER BY id ASC")
    fun getAllLessonProgressions(): LiveData<List<LessonProgression>>

    @Update
    suspend fun updateLessonProgression(lessonProgression: LessonProgression)

    @Delete
    suspend fun delete(lessonProgression: LessonProgression)
}