package com.github.talkbacktutorial.activities.modules.goback

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityBasicFrameBinding

class GoBackActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasicFrameBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_basic_frame)
        supportFragmentManager.commit {
            replace(binding.frame.id, GoBackPart1Fragment.newInstance())
        }
    }
}
