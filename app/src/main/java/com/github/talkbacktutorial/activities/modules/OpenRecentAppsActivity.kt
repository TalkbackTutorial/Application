package com.github.talkbacktutorial.activities.modules

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityModuleOpenRecentAppsBinding

class OpenRecentAppsActivity : AppCompatActivity() {

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