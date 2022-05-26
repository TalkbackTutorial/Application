package com.github.talkbacktutorial.activities.modules.addspacesnlbraillekeyboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityAddSpacesNlBrailleKeyboardBinding

class AddSpacesNLBrailleKeyboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddSpacesNlBrailleKeyboardBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_spaces_nl_braille_keyboard)
        supportFragmentManager.commit {
            replace(R.id.frame, AddSpacesNLBrailleKeyboardPart1Fragment())
        }
    }

}