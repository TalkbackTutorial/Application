package com.github.talkbacktutorial.activities.modules.virtualkeyboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityVirtualKeyboardModuleBinding

class VirtualKeyboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityVirtualKeyboardModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_virtual_keyboard_module)
        supportFragmentManager.commit {
            replace(R.id.frame1, VirtualKeyboardPart1Fragment.newInstance())
        }
    }
}