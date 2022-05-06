package com.github.talkbacktutorial.activities.modules.opennotifications

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivityOpenNotificationModuleBinding

class OpenNotificationModuleActivity : AppCompatActivity() {

    lateinit var binding: ActivityOpenNotificationModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_open_notification_module)
        supportFragmentManager.commit {
            replace(R.id.frame, (OpenNotificationModuleFragment()))
            addToBackStack("openNotification")
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