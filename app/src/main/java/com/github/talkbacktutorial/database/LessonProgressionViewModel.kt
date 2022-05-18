package com.github.talkbacktutorial.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.talkbacktutorial.lessons.LessonContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This view model acts as a communication centre between the repository and the UI
 * @param application the application context for the view model
 * @author Antony Loose
 */
class LessonProgressionViewModel(application: Application): AndroidViewModel(application){

    val getAllLessonProgressions: LiveData<List<LessonProgression>>
    private val repository: LessonProgressionRepository

    init {
        val lessonProgressionDao = LessonDatabase.getDatabase(application).lessonProgressionDao()
        repository = LessonProgressionRepository(lessonProgressionDao)
        getAllLessonProgressions = repository.getAllLessonProgressions
    }

    /**
     * Gets a lesson progression by number from the lesson database
     * @param lessonNum the number of the lesson you want to get
     * @author Antony Loose
     */
    fun getLessonProgression(lessonNum: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.getLessonProgression(lessonNum)
        }
    }

    /**
     * Updates a lesson progression in the lesson database
     * @param lessonProgression the lesson progression you want to update
     * @author Antony Loose
     */
    fun updateLessonProgression(lessonProgression: LessonProgression){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateLessonProgression(lessonProgression)
        }
    }

    /**
     * Adds a lesson progression to the lesson database
     * @param lessonProgression the lesson progression you want to add
     */
    fun addLessonProgression(lessonProgression: LessonProgression){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLessonProgression(lessonProgression)
        }
    }

    /**
     * Fills the database with all the lessons in the database container
     * @author Antony Loose
     */
    fun fillDatabase(){
        viewModelScope.launch(Dispatchers.IO){
            // if there are no lessons in the database, create lessons, add them to the db and this activities list of lessons
            for (lesson in LessonContainer.getAllLessons()) {
                val lp = LessonProgression(lesson.sequenceNumeral-1, false, 0)
                repository.addLessonProgression(lp)
            }
        }
    }

    /**
     * Deletes all lesson progressions in the lesson database
     * @author Antony Loose
     */
    fun clearDatabase(){
        viewModelScope.launch(Dispatchers.IO){
            // if there are no lessons in the database, create lessons, add them to the db and this activities list of lessons
            for (lesson in LessonContainer.getAllLessons()) {
                val lp = LessonProgression(lesson.sequenceNumeral-1, false, 0)
                repository.deleteLessonProgression(lp)
            }
        }
    }

}