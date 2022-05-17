package com.github.talkbacktutorial.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LessonProgressionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLessonProgression(lessonProgression: LessonProgression)

    @Query("SELECT * FROM lesson_table WHERE id == :lessonNum")
    fun getLessonProgression(lessonNum: Int): LiveData<LessonProgression>

    @Query("SELECT * FROM lesson_table ORDER BY id ASC")
    fun getAllLessonProgressions(): LiveData<List<LessonProgression>>

    @Update
    suspend fun updateLessonProgression(lessonProgression: LessonProgression)
}