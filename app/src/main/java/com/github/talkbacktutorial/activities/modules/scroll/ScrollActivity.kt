package com.github.talkbacktutorial.activities.modules.scroll

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.databinding.ActivityScrollingModuleBinding

class ScrollActivity : AppCompatActivity() {

    lateinit var binding: ActivityScrollingModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling_module)
        supportFragmentManager.commit {
            replace(R.id.frame, ScrollPart1Fragment.newInstance())
            addToBackStack("scrollingModulePart1")
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 1) {
            super.onBackPressed()
        } else {
            val intent = Intent(this, LessonActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}
