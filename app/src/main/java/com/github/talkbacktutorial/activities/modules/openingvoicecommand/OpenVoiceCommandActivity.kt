package com.github.talkbacktutorial.activities.modules.openingvoicecommand

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityOpenVoiceCommandModuleBinding

// This constant is needed to verify the audio permission result
private const val ASR_PERMISSION_REQUEST_CODE = 0
class OpenVoiceCommandActivity : AppCompatActivity() {

    lateinit var binding: ActivityOpenVoiceCommandModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_open_voice_command_module)
        verifyAudioPermissions()
        supportFragmentManager.commit {
            replace(R.id.frame, OpenVoiceCommandPart1Fragment.newInstance())
        }
    }

    /**
     * Checks for audio permissions for voice commands
     * @author Mohak Malhotra
     */
    private fun verifyAudioPermissions() {
        if (checkCallingOrSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                ASR_PERMISSION_REQUEST_CODE
            )
        }
    }

}

