package com.github.talkbacktutorial.activities.modules.adjustreadingcontrols

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityAdjustReadingControlsModuleBinding

class AdjustReadingControlsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdjustReadingControlsModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_adjust_reading_controls_module)

        supportFragmentManager.commit {
            replace(R.id.frame, AdjustReadingControlsPart1Fragment())
        }
    }
}
