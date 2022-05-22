package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityBasicFrameBinding

/**
 * Activity implementing the module for learning how to use the external app "Simple Voice
 * Recorder".
 *
 * @author Matthew Crossman
 */
class VoiceRecorderAppActivity : AppCompatActivity() {
    private val TUTORIAL_PREFIX = "TBT"
    private val EXTERNAL_APP_TAG = "SVR"
    private val APP_ACTION_KEY = "${TUTORIAL_PREFIX}_${EXTERNAL_APP_TAG}_ACTION"
    private val FINISH_RECORDING_TAG = "${EXTERNAL_APP_TAG}_TASK_FINISH_RECORDING"
    private val PLAY_RECORDING_TAG = "${EXTERNAL_APP_TAG}_TASK_PLAY_RECORDING"

    lateinit var binding: ActivityBasicFrameBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_basic_frame)

        supportFragmentManager.commit {
            replace(R.id.frame, VoiceRecorderIntroFragment())
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        /*
            New intent usually means this activity was called from elsewhere after first start.
            This is probably the external app.
         */
        intent?.let {
            // get extras
            val actionCompleted = intent.getStringExtra(APP_ACTION_KEY)

            actionCompleted?.let {
                // move on with module based on tag IF intro is the current fragment
                if (actionCompleted == PLAY_RECORDING_TAG && supportFragmentManager.fragments.first() is VoiceRecorderIntroFragment)
                    supportFragmentManager.commit {
                        replace(R.id.frame, VoiceRecorderPlayRecordingFragment())
                    }
            }
        }
    }
}