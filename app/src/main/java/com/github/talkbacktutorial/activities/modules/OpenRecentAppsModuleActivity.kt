package com.github.talkbacktutorial.activities.modules

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part1Fragment
import com.github.talkbacktutorial.databinding.ActivityLesson0Binding
import com.github.talkbacktutorial.databinding.ActivityModuleOpenRecentAppsBinding

class OpenRecentAppsModuleActivity : AppCompatActivity() {

    lateinit var binding: ActivityModuleOpenRecentAppsBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_module_open_recent_apps)
        supportFragmentManager.commit {
            replace(R.id.frame, OpenRecentAppsPart1Fragment())
        }
    }

}