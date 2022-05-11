package com.github.talkbacktutorial.activities.modules.explorebytouch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivityExploreMenuByTouchModuleBinding

class ExploreMenuByTouchActivity : AppCompatActivity() {

    lateinit var binding: ActivityExploreMenuByTouchModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_explore_menu_by_touch_module)
        supportFragmentManager.commit {
            replace(R.id.frame, ExploreMenuByTouchPart1Fragment.newInstance())
        }
    }
}
