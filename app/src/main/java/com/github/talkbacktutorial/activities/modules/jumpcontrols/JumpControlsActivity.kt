package com.github.talkbacktutorial.activities.modules.jumpcontrols

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationPart1Fragment
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationPart1Fragment.NavigationMode
import com.github.talkbacktutorial.databinding.ActivityJumpNavigationGenericIntroBinding

/**
 * Implements Jumping Controls - learning how to jump from control to control.
 *
 * @author Matthew Crossman
 */
class JumpControlsActivity : AppCompatActivity() {
    lateinit var binding: ActivityJumpNavigationGenericIntroBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_jump_navigation_generic_intro)

        supportFragmentManager.commit {
            replace(R.id.frame, JumpNavigationPart1Fragment(NavigationMode.CONTROLS))
        }
    }
}
