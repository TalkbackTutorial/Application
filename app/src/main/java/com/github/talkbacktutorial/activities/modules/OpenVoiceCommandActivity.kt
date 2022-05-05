package com.github.talkbacktutorial.activities.modules

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivityModuleOpenVoiceCommandBinding

class OpenVoiceCommandActivity : AppCompatActivity() {

    lateinit var binding: ActivityModuleOpenVoiceCOmmandBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_module_open_voice_command)
        supportFragmentManager.commit {
            replace(R.id.frame, OpenVoiceCommandFragment())
            addToBackStack("openVoiceCommands")
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