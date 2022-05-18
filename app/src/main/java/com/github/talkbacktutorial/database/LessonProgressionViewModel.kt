package com.github.talkbacktutorial.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.talkbacktutorial.lessons.LessonContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LessonProgressionViewModel(application: Application): AndroidViewModel(application){

    val getAllLessonProgressions: LiveData<List<LessonProgression>>
    private val repository: LessonProgressionRepository

    init {
        val lessonProgressionDao = LessonDatabase.getDatabase(application).lessonProgressionDao()
        repository = LessonProgressionRepository(lessonProgressionDao)
        getAllLessonProgressions = repository.getAllLessonProgressions
    }

    fun getLessonProgression(lessonNum: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.getLessonProgression(lessonNum)
        }
    }

    fun updateLessonProgression(lessonProgression: LessonProgression){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateLessonProgression(lessonProgression)
        }
    }

    fun addLessonProgression(lessonProgression: LessonProgression){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLessonProgression(lessonProgression)
        }
    }

    fun fillDatabase(){
        viewModelScope.launch(Dispatchers.IO){
            // if there are no lessons in the database, create lessons, add them to the db and this activities list of lessons
            val lps = ArrayList<LessonProgression>()
            for (lesson in LessonContainer.getAllLessons()) {
                val lp = LessonProgression(lesson.sequenceNumeral - 1, false, 0)
                lps.add(lp)
                repository.addLessonProgression(lp)
            }
        }
    }

}