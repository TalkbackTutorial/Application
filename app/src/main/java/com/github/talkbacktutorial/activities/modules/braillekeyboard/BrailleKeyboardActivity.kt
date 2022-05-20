package com.github.talkbacktutorial.activities.modules.braillekeyboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityBrailleKeyboardModuleBinding

class BrailleKeyboardActivity: AppCompatActivity() {

    lateinit var binding: ActivityBrailleKeyboardModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_braille_keyboard_module)
        supportFragmentManager.commit {
            replace(R.id.frame1, BrailleKeyboardPart1Fragment.newInstance())
        }
    }
}