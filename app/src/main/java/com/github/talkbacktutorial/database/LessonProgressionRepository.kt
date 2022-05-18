package com.github.talkbacktutorial.database

import androidx.lifecycle.LiveData

/**
 * The repository used to manipulate data within the database.
 * The repository is in place to abstract the access to multiple data sources
 * @author Antony Loose
 */
class LessonProgressionRepository(private val lessonProgressionDao: LessonProgressionDao) {

    val getAllLessonProgressions: LiveData<List<LessonProgression>> = lessonProgressionDao.getAllLessonProgressions()

    fun getLessonProgression(lessonNum: Int): LiveData<LessonProgression> {
        return lessonProgressionDao.getLessonProgression(lessonNum)
    }

    suspend fun updateLessonProgression(lessonProgression: LessonProgression){
        lessonProgressionDao.updateLessonProgression(lessonProgression)
    }

    suspend fun addLessonProgression(lessonProgression: LessonProgression){
        lessonProgressionDao.addLessonProgression(lessonProgression)
    }

    suspend fun deleteLessonProgression(lessonProgression: LessonProgression){
        lessonProgressionDao.delete(lessonProgression)
    }
}