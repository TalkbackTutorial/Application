package com.github.talkbacktutorial.activities.modules.jumpheaders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationPart1Fragment
import com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationPart1Fragment.NavigationMode
import com.github.talkbacktutorial.databinding.ActivityJumpNavigationGenericIntroBinding

/**
 * Implements jumping headers - learning how to jump between headers on a page
 *
 * @author Matthew Crossman
 */
class JumpHeadersActivity : AppCompatActivity() {
    lateinit var binding: ActivityJumpNavigationGenericIntroBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_jump_navigation_generic_intro)

        supportFragmentManager.commit {
            replace(R.id.frame, JumpNavigationPart1Fragment(NavigationMode.HEADERS))
        }
    }
}
