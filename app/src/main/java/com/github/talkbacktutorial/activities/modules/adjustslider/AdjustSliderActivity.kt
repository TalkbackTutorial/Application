package com.github.talkbacktutorial.activities.modules.adjustslider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityAdjustSliderModuleBinding

class AdjustSliderActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdjustSliderModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_adjust_slider_module)
        supportFragmentManager.commit {
            replace(R.id.frame, AdjustSliderPart1Fragment.newInstance())
        }
    }
}
