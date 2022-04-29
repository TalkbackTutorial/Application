package com.github.talkbacktutorial.activities.modules.mediavolumecontrol

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivityMediaVolumeControlModuleBinding

class MediaVolumeControlModuleActivity: AppCompatActivity() {
    lateinit var binding: ActivityMediaVolumeControlModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_media_volume_control_module)
        supportFragmentManager.commit {
            replace(R.id.frame1, MediaVolumeControlPart1Fragment.newInstance())
            addToBackStack("mediaVolumeControlPart1")
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