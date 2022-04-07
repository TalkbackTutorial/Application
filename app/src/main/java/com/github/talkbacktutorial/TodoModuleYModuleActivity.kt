package com.github.talkbacktutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.databinding.ActivityTodoModuleYModuleBinding

class TodoModuleYModuleActivity : AppCompatActivity() { // E.g. PausingAndPlayingMediaModuleActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        // E.g. activity_pausing_and_playing_media_module
        val binding: ActivityTodoModuleYModuleBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_module_y_module)
    }

}