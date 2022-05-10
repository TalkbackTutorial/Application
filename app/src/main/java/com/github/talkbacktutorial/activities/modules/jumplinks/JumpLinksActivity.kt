package com.github.talkbacktutorial.activities.modules.jumplinks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationIntroFragment
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationIntroFragment.NavigationMode
import com.github.talkbacktutorial.databinding.ActivityJumpNavigationGenericIntroBinding

/**
 * Teaches user how to jump between links.
 *
 * @author Matthew Crossman
 */
class JumpLinksActivity : AppCompatActivity() {
    lateinit var binding: ActivityJumpNavigationGenericIntroBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_jump_navigation_generic_intro)

        supportFragmentManager.commit {
            replace(R.id.frame, JumpNavigationIntroFragment(NavigationMode.LINKS))
        }
    }
}