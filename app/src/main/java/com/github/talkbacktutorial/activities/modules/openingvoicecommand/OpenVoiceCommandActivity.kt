package com.github.talkbacktutorial.activities.modules.openingvoicecommand

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.activities.modules.OpenVoiceCommandFragment
import com.github.talkbacktutorial.databinding.ActivityOpenVoiceCommandBinding

class OpenVoiceCommandActivity : AppCompatActivity() {

    lateinit var binding: ActivityOpenVoiceCommandBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_open_voice_command)
        supportFragmentManager.commit {
            replace(R.id.frame, OpenVoiceCommandFragment())
            addToBackStack("openVoiceCommands")
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 1) {
            super.onBackPressed()
        } else {
            val intent = Intent(this, LessonActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

}