package com.github.talkbacktutorial.activities.modules.jumptext

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityJumpTextModuleBinding

class JumpTextActivity : AppCompatActivity() {

    lateinit var binding: ActivityJumpTextModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_jump_text_module)

        supportFragmentManager.commit {
            replace(R.id.frame, JumpTextPart1Fragment())
        }
    }
}
