package com.github.talkbacktutorial.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The data access object for accessing the progression of lessons
 * @author Antony Loose
 */
@Dao
interface ModuleProgressionDao {

    @Insert
    suspend fun addModuleProgression(moduleProgression: ModuleProgression)

    @Query("SELECT * FROM module_table WHERE moduleName == :moduleName")
    fun getModuleProgression(moduleName: String): LiveData<ModuleProgression>

    @Query("SELECT * FROM module_table ORDER BY lessonNum ASC")
    fun getAllModuleProgressions(): LiveData<List<ModuleProgression>>

    @Query("SELECT * FROM module_table WHERE lessonNum == :lessonNum")
    fun getModuleProgressionByLessonNum(lessonNum: Int): LiveData<List<ModuleProgression>>

    @Update
    suspend fun updateModuleProgression(moduleProgression: ModuleProgression)

    @Delete
    suspend fun deleteModuleProgression(moduleProgression: ModuleProgression)

    @Query("DELETE FROM module_table")
    suspend fun wipeTable()
}