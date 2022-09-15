package com.github.talkbacktutorial.activities.modules.calculatorapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityBasicFrameBinding

class CalculatorAppActivity : AppCompatActivity() {
    private val tutorialPrefix = "TBT"
    private val externalAppTag = "SC"
    private val appActionKey = tutorialPrefix + "_" + externalAppTag + "_ACTION"
    private val finishCalculatorTag = externalAppTag + "_TASK_FINISH_CALCULATOR"

    lateinit var binding: ActivityBasicFrameBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_basic_frame)
        supportFragmentManager.commit {
            replace(R.id.frame, CalculatorAppIntroFragment())
        }
    }

    /**
     * Handles intents targeting this activity made after it was initialised (onCreate). This is
     * used to manage intents dispatched by our forked Simple Calculator.
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            // get extras
            val actionCompleted = intent.getStringExtra(appActionKey)
            actionCompleted?.let {
                // move on with module based on tag IF intro is the current fragment
                 if (actionCompleted == finishCalculatorTag) {
                    supportFragmentManager.commit {
                        replace(R.id.frame, CalculatorAppPart2Fragment.newInstance())
                    }
                }
            }
        }
    }
}