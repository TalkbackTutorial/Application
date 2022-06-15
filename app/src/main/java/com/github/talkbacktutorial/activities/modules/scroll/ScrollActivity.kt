package com.github.talkbacktutorial.activities.modules.scroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
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
        }
    }
}
