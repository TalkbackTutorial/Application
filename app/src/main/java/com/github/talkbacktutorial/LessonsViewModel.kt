package com.github.talkbacktutorial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LessonsViewModel : ViewModel() {

    val lesson1Locked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val lesson2Locked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }
    val lesson3Locked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }
    val lesson4Locked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }

}