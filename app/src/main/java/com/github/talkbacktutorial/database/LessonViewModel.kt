package com.github.talkbacktutorial.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LessonViewModel(application: Application): AndroidViewModel(application){

    private val getAllLessonProgressions: LiveData<List<LessonProgression>>
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
}