package com.github.talkbacktutorial.activities.modules.adjustingmenusliders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivitySelectSettingModuleBinding

class SelectSettingModuleActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding: ActivitySelectSettingModuleBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_select_setting_module
        )
        supportFragmentManager.commit {
            replace(R.id.frame, SelectSettingPart1Fragment.newInstance())
            addToBackStack("lesson2module2selectsetting")
        }
    }
}