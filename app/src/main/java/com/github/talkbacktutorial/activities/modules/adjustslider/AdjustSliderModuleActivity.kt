package com.github.talkbacktutorial.activities.modules.adjustslider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivityAdjustSliderModuleBinding

class AdjustSliderModuleActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdjustSliderModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_adjust_slider_module)
        supportFragmentManager.commit {
            replace(R.id.frame, AdjustSliderModulePart1Fragment.newInstance())
            addToBackStack("adjustSliderModulePart1")
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