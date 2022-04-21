package com.github.talkbacktutorial.activities.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.lesson2.Lesson2Module1P2Fragment
import com.github.talkbacktutorial.activities.lesson2.scrollingmodule.ScrollingModulePart1Fragment
import com.github.talkbacktutorial.databinding.ActivityScrollingModuleBinding

class ScrollingModuleActivity : AppCompatActivity() {

    lateinit var binding: ActivityScrollingModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling_module)
        supportFragmentManager.commit {
            replace(R.id.frame, ScrollingModulePart1Fragment.newInstance())
            addToBackStack("scrollingModulePart1")
        }
    }

}