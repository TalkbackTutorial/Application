package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.content.ComponentName
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

    private lateinit var stage: VoiceRecorderStage

    lateinit var binding: ActivityBasicFrameBinding
        private set

    companion object {
        private val appIntent = Intent()

        const val packageName = "com.simplemobiletools.voicerecorder.tbtutorialfork.debug"
        const val targetClassName = "com.simplemobiletools.voicerecorder.activities.MainActivity"

        /**
         * Gets the intent to open the external app needed by this activity and its fragments.
         * @author Matthew Crossman
         * @return an intent that can be used with startActivity
         */
        fun getAppIntent(): Intent {
            appIntent.component = ComponentName(
                packageName,
                targetClassName
            )

            return appIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_basic_frame)

        stage = VoiceRecorderStage.MAKE_RECORDING

        supportFragmentManager.commit {
            replace(R.id.frame, VoiceRecorderIntroFragment())
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // New intent usually means this activity was called from elsewhere after first start.
        // This is probably the external app.
        intent?.let {
            // get extras
            val actionCompleted = intent.getStringExtra(APP_ACTION_KEY)

            actionCompleted?.let {
                // move on with module based on tag IF intro is the current fragment
                if (actionCompleted == FINISH_RECORDING_TAG && stage == VoiceRecorderStage.MAKE_RECORDING) {
                    // update stage
                    stage = VoiceRecorderStage.PLAY_RECORDING

                    supportFragmentManager.commit {
                        replace(R.id.frame, VoiceRecorderPlayRecordingFragment())
                    }
                } else if (actionCompleted == PLAY_RECORDING_TAG && stage == VoiceRecorderStage.PLAY_RECORDING) {
                    stage = VoiceRecorderStage.FINISHED

                    supportFragmentManager.commit {
                        replace(R.id.frame, VoiceRecorderFinishedFragment())
                    }
                } else {
                    // wrong move
                    supportFragmentManager.commit {
                        replace(R.id.frame, VoiceRecorderWrongActionFragment(stage))
                    }
                }
            }
        }
    }

    enum class VoiceRecorderStage {
        MAKE_RECORDING, PLAY_RECORDING, FINISHED
    }

}