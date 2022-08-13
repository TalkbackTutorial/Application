package com.github.talkbacktutorial.activities.modules.opentalkbackmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityOpenTalkbackMenuModuleBinding

class OpenTalkbackMenuActivity : AppCompatActivity() {

    lateinit var binding: ActivityOpenTalkbackMenuModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_open_talkback_menu_module)
        supportFragmentManager.commit {
            replace(R.id.frame, OpenTalkBackMenuPart1Fragment())
        }
    }
}
