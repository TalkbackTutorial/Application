package com.github.talkbacktutorial.database

class InstanceSingleton {
    var selectedLessonNumber: Int? = null
    var selectedModuleName: String? = null

    companion object {
        @Volatile
        private var INSTANCE: InstanceSingleton? = null

        fun getInstanceSingleton(): InstanceSingleton {
            val tmpInstance = INSTANCE
            if (tmpInstance != null){
                return tmpInstance
            }
            synchronized(this){
                val instance = InstanceSingleton()
                INSTANCE = instance
                return instance
            }
        }
    }
}