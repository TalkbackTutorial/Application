package com.github.talkbacktutorial.activities.modules.dnlbraillekeyboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityDnlBrailleKeyboardBinding

class DNLBrailleKeyboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDnlBrailleKeyboardBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_dnl_braille_keyboard)
        supportFragmentManager.commit {
            replace(R.id.frame, DNLBrailleKeyboardPart1Fragment())
        }
    }

}