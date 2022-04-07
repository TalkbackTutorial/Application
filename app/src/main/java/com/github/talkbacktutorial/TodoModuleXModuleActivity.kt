package com.github.talkbacktutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.databinding.ActivityTodoModuleXModuleBinding

class TodoModuleXModuleActivity : AppCompatActivity() { // E.g. PausingAndPlayingMediaModuleActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        // E.g. activity_pausing_and_playing_media_module
        val binding: ActivityTodoModuleXModuleBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_module_x_module)
    }

}