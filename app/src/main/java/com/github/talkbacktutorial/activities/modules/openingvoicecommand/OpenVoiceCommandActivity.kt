package com.github.talkbacktutorial.activities.modules.openingvoicecommand

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.activities.modules.OpenVoiceCommandPart1Fragment
import com.github.talkbacktutorial.databinding.ActivityOpenVoiceCommandModuleBinding

class OpenVoiceCommandActivity : AppCompatActivity() {

    lateinit var binding: ActivityOpenVoiceCommandModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_open_voice_command_module)
        supportFragmentManager.commit {
            replace(R.id.frame, OpenVoiceCommandPart1Fragment())
        }
    }
}
