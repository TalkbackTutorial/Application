package com.github.talkbacktutorial.database

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.github.talkbacktutorial.lessons.LessonContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

/**
 * This view model acts as a communication centre between the repository and the UI
 * @param application the application context for the view model
 * @author Antony Loose, Jade Davis
 */
class ModuleProgressionViewModel(application: Application): AndroidViewModel(application){

    val getAllModuleProgressions: LiveData<List<ModuleProgression>>
    private val repository: ModuleProgressionRepository

    init {
        val lessonProgressionDao = TMTDatabase.getDatabase(application).moduleProgressionDao()
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

    /**
     * Gets module progression in the module database based on lesson number
     * @param moduleProgression the module progression you want to update
     * @author Antony Loose
     */
    fun getModuleProgressionByLessonNum(lessonNum: Int): LiveData<List<ModuleProgression>>{
        return repository.getModuleProgressionByLessonNum(lessonNum)
    }

    /**
     * Updates a module progression in the module database
     * @param moduleProgression the module progression you want to update
     * @author Antony Loose
     */
    fun updateModuleProgression(moduleProgression: ModuleProgression){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateModuleProgression(moduleProgression)
        }
    }

    /**
     * Adds a module progression to the module database
     * @param moduleProgression the module progression you want to add
     * @author Antony Loose
     */
    fun addModuleProgression(moduleProgression: ModuleProgression){
        viewModelScope.launch(Dispatchers.IO){
            repository.addModuleProgression(moduleProgression)
        }
    }

    /**
     * Fills the database with all the modules in the database container
     * @author Antony Loose
     */
    fun fillDatabase(){
        viewModelScope.launch(Dispatchers.IO){
            for (lesson in LessonContainer.getAllLessons()) {
                for (module in lesson.modules) {
                    val mp = ModuleProgression(0, module.title, false, lesson.sequenceNumeral)
                    repository.addModuleProgression(mp)
                }
            }
        }
    }

    /**
     * Deletes all module progressions in the module database
     * @author Jade Davis
     */
    fun clearDatabase(){
        viewModelScope.launch(Dispatchers.IO){
            repository.wipeAllModuleProgressions()
            exitProcess(0)
        }
    }

    /**
     * Marks the module as completed in the database
     * @param moduleName the name of the module to be marked as complete
     * @param context the context of the application
     * @author Jade Davis
     */
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