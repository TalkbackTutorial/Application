package com.github.talkbacktutorial.activities.modules

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivityModuleOpenRecentAppsBinding

class OpenRecentAppsActivity : AppCompatActivity() {

    lateinit var binding: ActivityModuleOpenRecentAppsBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_module_open_recent_apps)
        supportFragmentManager.commit {
            replace(R.id.frame, OpenRecentAppsPart1Fragment())
            addToBackStack("openRecentAppsPart1")
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 1) {
            super.onBackPressed()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

}