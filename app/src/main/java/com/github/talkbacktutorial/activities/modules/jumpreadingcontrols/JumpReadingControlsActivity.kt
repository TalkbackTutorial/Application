package com.github.talkbacktutorial.activities.modules.jumpreadingcontrols

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityJumpReadingControlsModuleBinding

class JumpReadingControlsActivity : AppCompatActivity() {
    /**
     * Data binding used to make manipulating the interface easier. Every element on the layout will
     * be exposed through the binding as a member.
     */
    lateinit var binding: ActivityJumpReadingControlsModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // this hides action bar at the top of the app
        supportActionBar?.hide()

        // bind activity to layout - this makes our binding work
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_jump_reading_controls_module)

        // ask the fragment manager to add our fragment, replacing the existing frame in the layout
        supportFragmentManager.commit {
            // fragments need to replace placeholders in the activity's layout
            replace(R.id.frame, JumpReadingControlsPart1Fragment())
            // if we have multiple fragments, we'll add to the back stack
        }
    }

}