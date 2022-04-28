package com.github.talkbacktutorial.activities.modules

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityLesson0Binding

class OpenRecentAppsModuleActivity : AppCompatActivity() {

    lateinit var binding: ActivityLesson0Binding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson0)

        supportFragmentManager.commit {
        replace(R.id.frame, OpenRecentAppsModuleFragment.newInstance())
        addToBackStack("openrecentappsmodule")
        }

    }
}