package com.github.talkbacktutorial.database

import androidx.lifecycle.LiveData

/**
 * The repository used to manipulate data within the database.
 * The repository is in place to abstract the access to multiple data sources
 * @author Antony Loose
 */
class ModuleProgressionRepository(private val moduleProgressionDao: ModuleProgressionDao) {

    val getAllModuleProgressions: LiveData<List<ModuleProgression>> = moduleProgressionDao.getAllModuleProgressions()

    fun getModuleProgression(moduleName: String): LiveData<ModuleProgression> {
        return moduleProgressionDao.getModuleProgression(moduleName)
    }

    fun getModuleProgressionByLessonNum(lessonNum: Int): LiveData<List<ModuleProgression>>{
        return moduleProgressionDao.getModuleProgressionByLessonNum(lessonNum)
    }

    suspend fun updateModuleProgression(moduleProgression: ModuleProgression){
        moduleProgressionDao.updateModuleProgression(moduleProgression)
    }

    suspend fun addModuleProgression(moduleProgression: ModuleProgression){
        moduleProgressionDao.addModuleProgression(moduleProgression)
    }

    suspend fun deleteModuleProgression(moduleProgression: ModuleProgression){
        moduleProgressionDao.deleteModuleProgression(moduleProgression)
    }

    suspend fun wipeAllModuleProgressions(){
        moduleProgressionDao.wipeTable()
    }
}