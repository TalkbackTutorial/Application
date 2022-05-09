package com.github.talkbacktutorial.activities.modules.jumpcontrols

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationIntroFragment
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationIntroFragment.NavigationMode.CONTROLS
import com.github.talkbacktutorial.databinding.ActivityJumpControlsModuleBinding

/**
 * Implements Jumping Controls - learning how to jump from control to control.
 *
 * @author Matthew Crossman
 */
class JumpControlsActivity : AppCompatActivity() {
    lateinit var binding: ActivityJumpControlsModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_jump_controls_module)

        supportFragmentManager.commit {
            replace(R.id.frame, JumpNavigationIntroFragment(CONTROLS))
        }
    }
}