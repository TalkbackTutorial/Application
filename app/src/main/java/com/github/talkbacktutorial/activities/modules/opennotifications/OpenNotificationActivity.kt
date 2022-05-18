package com.github.talkbacktutorial.activities.modules.opennotifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityOpenNotificationModuleBinding

class OpenNotificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityOpenNotificationModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_open_notification_module)
        supportFragmentManager.commit {
            replace(R.id.frame, OpenNotificationPart1Fragment.newInstance())
        }
    }
}
