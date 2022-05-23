package com.github.talkbacktutorial.database

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.lessons.LessonContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

/**
 * This view model acts as a communication centre between the repository and the UI
 * @param application the application context for the view model
 * @author Antony Loose
 */
class ModuleProgressionViewModel(application: Application): AndroidViewModel(application){

    val getAllModuleProgressions: LiveData<List<ModuleProgression>>
    private val repository: ModuleProgressionRepository

    init {
        val lessonProgressionDao = ModuleDatabase.getDatabase(application).moduleProgressionDao()
        repository = ModuleProgressionRepository(lessonProgressionDao)
        getAllModuleProgressions = repository.getAllModuleProgressions
    }

    /**
     * Gets a lesson progression by number from the lesson database
     * @param moduleName the name of the module you want to get
     * @author Antony Loose
     */
    fun getModuleProgression(moduleName: String): LiveData<ModuleProgression>{
        return repository.getModuleProgression(moduleName)
    }

    fun getModuleProgressionByLessonNum(lessonNum: Int): LiveData<List<ModuleProgression>>{
        return repository.getModuleProgressionByLessonNum(lessonNum)
    }

    /**
     * Updates a lesson progression in the lesson database
     * @param moduleProgression the module progression you want to update
     * @author Antony Loose
     */
    fun updateModuleProgression(moduleProgression: ModuleProgression){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateModuleProgression(moduleProgression)
        }
    }

    /**
     * Adds a lesson progression to the lesson database
     * @param moduleProgression the lesson progression you want to add
     */
    fun addModuleProgression(moduleProgression: ModuleProgression){
        viewModelScope.launch(Dispatchers.IO){
            repository.addModuleProgression(moduleProgression)
        }
    }

    /**
     * Fills the database with all the lessons in the database container
     * @author Antony Loose
     */
    fun fillDatabase(){
        viewModelScope.launch(Dispatchers.IO){
            // if there are no lessons in the database, create lessons, add them to the db and this activities list of lessons
            var index = 0
            for (lesson in LessonContainer.getAllLessons()) {
                for(module in lesson.modules){
                    val mp = ModuleProgression(index, module.title, false, lesson.sequenceNumeral)
                    index++
                    repository.addModuleProgression(mp)
                }
            }
        }
    }

    /**
     * Deletes all lesson progressions in the lesson database
     * @author Jade Davis
     */
    fun clearDatabase(){
        viewModelScope.launch(Dispatchers.IO){
            repository.wipeAllModuleProgressions()
            exitProcess(0);
        }
    }

    //MainActivity.applicationContext()
    fun markModuleCompleted(moduleName: String, context: Context) {
        var moduleUpdated = false
        this.getModuleProgression(moduleName).observe(context as LifecycleOwner, Observer { module ->
            if (!moduleUpdated) {
                module.completed = true
                this.updateModuleProgression(module)
                moduleUpdated = true
            }
        })
    }

}