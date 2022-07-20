package com.github.talkbacktutorial.activities.modules.calculatorapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityCalculatorAppModuleBinding

class CalculatorAppActivity : AppCompatActivity() {
    private val TUTORIAL_PREFIX = getString(R.string.tbt)
    private val EXTERNAL_APP_TAG = getString(R.string.sc)
    private val APP_ACTION_KEY = TUTORIAL_PREFIX + getString(R.string.underscore) + EXTERNAL_APP_TAG + getString(R.string.action)
    private val FINISH_CALCULATOR_TAG = EXTERNAL_APP_TAG + getString(R.string.task_finish_calculator)

    lateinit var binding: ActivityCalculatorAppModuleBinding
        private set

    companion object {
        private val appIntent = Intent()

        /**
         * Gets the intent to open the external app needed by this activity and its fragments.
         * @return an intent that can be used with startActivity
         */
        fun getAppIntent(): Intent {
            appIntent.component = ComponentName(
                "com.simplemobiletools.calculatorapp.debug",
                "com.simplemobiletools.calculator.activities.SplashActivity.Orange"
            )

            return appIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator_app_module)
        supportFragmentManager.commit {
            replace(R.id.frame, CalculatorAppPart1Fragment.newInstance())
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
            val actionCompleted = intent.getStringExtra(APP_ACTION_KEY)
            actionCompleted?.let {
                // move on with module based on tag IF intro is the current fragment
                 if (actionCompleted == FINISH_CALCULATOR_TAG) {
                    supportFragmentManager.commit {
                        replace(R.id.frame, CalculatorAppPart2Fragment())
                    }
                } else {
                    // wrong move
                }
            }
        }
    }
}