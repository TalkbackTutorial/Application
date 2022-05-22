package com.github.talkbacktutorial.activities.modules.jumpcontrols

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationPart1Fragment
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationPart1Fragment.NavigationMode
import com.github.talkbacktutorial.databinding.ActivityBasicFrameBinding

/**
 * Activity implementing the jump controls module.
 *
 * Uses a common fragment across the jump navigation modules.
 *
 * @author Matthew Crossman
 */
class JumpControlsActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasicFrameBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_basic_frame)

        supportFragmentManager.commit {
            replace(R.id.frame, JumpNavigationPart1Fragment(NavigationMode.CONTROLS))
        }
    }
}
