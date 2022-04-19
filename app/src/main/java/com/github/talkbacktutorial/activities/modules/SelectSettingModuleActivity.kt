package com.github.talkbacktutorial.activities.modules

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.lesson2.Lesson2Module1P2Fragment
import com.github.talkbacktutorial.activities.lesson2.selectsettingmodule.SelectSettingPart1Fragment
import com.github.talkbacktutorial.databinding.ActivitySelectSettingModuleBinding

class SelectSettingModuleActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding: ActivitySelectSettingModuleBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_select_setting_module
        )
//        supportFragmentManager.commit {
//            replace(R.id.frame, SelectSettingPart1Fragment.newInstance())
//            addToBackStack("SelectSettingPart1Fragment")
//        }
    }
}