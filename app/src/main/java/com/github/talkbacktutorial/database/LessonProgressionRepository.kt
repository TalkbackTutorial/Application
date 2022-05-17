package com.github.talkbacktutorial.database

import androidx.lifecycle.LiveData

class LessonProgressionRepository(private val lessonProgressionDao: LessonProgressionDao) {

    val getAllLessonProgressions: LiveData<List<LessonProgression>> = lessonProgressionDao.getAllLessonProgressions()

    fun getLessonProgression(lessonNum: Int): LiveData<LessonProgression>{
        return lessonProgressionDao.getLessonProgression(lessonNum)
    }

    suspend fun updateLessonProgression(lessonProgression: LessonProgression){
        lessonProgressionDao.updateLessonProgression(lessonProgression)
    }

    suspend fun addLessonProgression(lessonProgression: LessonProgression){
        lessonProgressionDao.addLessonProgression(lessonProgression)
    }
}