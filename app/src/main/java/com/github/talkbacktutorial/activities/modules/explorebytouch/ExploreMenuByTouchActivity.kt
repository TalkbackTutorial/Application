package com.github.talkbacktutorial.activities.modules.explorebytouch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivityExploreMenuByTouchBinding

/**
 * Explore menu by touch activity
 * @author Jason Wu
 */
class ExploreMenuByTouchActivity : AppCompatActivity() {

    lateinit var binding: ActivityExploreMenuByTouchBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_explore_menu_by_touch)
        supportFragmentManager.commit {
            replace(R.id.frame, ExploreMenuByTouchPart1Fragment.newInstance())
            addToBackStack("exploreMenuModulePart1")
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 1) {
            super.onBackPressed()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}