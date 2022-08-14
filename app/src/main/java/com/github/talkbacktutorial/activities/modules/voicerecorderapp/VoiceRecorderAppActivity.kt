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
    private val tutorialPrefix = "TBT"
    private val externalAppTag = "SVR"
    private val appActionKey = "${tutorialPrefix}_${externalAppTag}_ACTION"
    private val finishRecordingTag = "${externalAppTag}_TASK_FINISH_RECORDING"
    private val playRecordingTag = "${externalAppTag}_TASK_PLAY_RECORDING"

    private lateinit var stage: VoiceRecorderStage

    lateinit var binding: ActivityBasicFrameBinding
        private set

    companion object {
        private val appIntent = Intent()

        const val packageName = "com.simplemobiletools.voicerecorder.tbtutorialfork.debug"
        private const val targetClassName = "com.simplemobiletools.voicerecorder.activities.MainActivity"

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

    /**
     * Handles intents targeting this activity made after it was initialised (onCreate). This is
     * used to manage intents dispatched by our forked Simple Voice Recorder.
     *
     * @author Matthew Crossman
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            // get extras
            val actionCompleted = intent.getStringExtra(appActionKey)

            actionCompleted?.let {
                // move on with module based on tag IF intro is the current fragment
                if (actionCompleted == finishRecordingTag && stage == VoiceRecorderStage.MAKE_RECORDING) {
                    // update stage
                    stage = VoiceRecorderStage.PLAY_RECORDING

                    supportFragmentManager.commit {
                        replace(R.id.frame, VoiceRecorderPlayRecordingFragment())
                    }
                } else if (actionCompleted == playRecordingTag && stage == VoiceRecorderStage.PLAY_RECORDING) {
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

    /**
     * Enum specifying different stages in the module that need to be tracked. These are used by
     * onNewIntent to determine what to show next. Stage updates are sent as extras in the intent
     * sent out by the external app.
     *
     * @author Matthew Crossman
     * @see onNewIntent
     */
    enum class VoiceRecorderStage {
        /**
         * The section teaching the user how to make a new voice recording. Currently the first
         * stage.
         */
        MAKE_RECORDING,

        /**
         * Teaching the user how to play back a previous recording. Should be after MAKE_RECORDING.
         */
        PLAY_RECORDING,

        /**
         * Marks the completion of the module, indicating that VoiceRecorderAppActivity should wrap
         * things up.
         */
        FINISHED
    }

}