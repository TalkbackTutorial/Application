package com.github.talkbacktutorial.activities.modules.virtualkeyboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityBasicFrameBinding

class VirtualKeyboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasicFrameBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_basic_frame)
        supportFragmentManager.commit {
            replace(binding.frame.id, VirtualKeyboardPart1Fragment.newInstance())
        }
    }
}