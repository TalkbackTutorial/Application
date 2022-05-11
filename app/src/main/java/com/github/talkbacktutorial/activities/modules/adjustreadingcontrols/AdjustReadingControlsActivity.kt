package com.github.talkbacktutorial.activities.modules.adjustreadingcontrols

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityAdjustReadingControlsModuleBinding

/**
 * Activity for learning how to adjust reading controls.
 *
 * @author Matthew Crossman
 * @see com.github.talkbacktutorial.lessons.modules.AdjustReadingControls
 */
class AdjustReadingControlsActivity : AppCompatActivity() {
    /**
     * Data binding used to make manipulating the interface easier. Every element on the layout will
     * be exposed through the binding as a member.
     */
    lateinit var binding: ActivityAdjustReadingControlsModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // this hides action bar at the top of the app
        supportActionBar?.hide()

        // bind activity to layout - this makes our binding work
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_adjust_reading_controls_module)

        // ask the fragment manager to add our fragment, replacing the existing frame in the layout
        supportFragmentManager.commit {
            // fragments need to replace placeholders in the activity's layout
            replace(R.id.frame, AdjustReadingControlsPart1Fragment())
            // if we have multiple fragments, we'll add to the back stack
        }
    }
}
