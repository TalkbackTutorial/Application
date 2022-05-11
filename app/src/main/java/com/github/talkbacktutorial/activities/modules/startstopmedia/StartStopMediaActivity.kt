package com.github.talkbacktutorial.activities.modules.startstopmedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivityStartStopMediaModuleBinding

class StartStopMediaActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartStopMediaModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_start_stop_media_module)
        supportFragmentManager.commit {
            replace(R.id.frame1, StartStopMediaPart1Fragment.newInstance())
        }
    }
}
