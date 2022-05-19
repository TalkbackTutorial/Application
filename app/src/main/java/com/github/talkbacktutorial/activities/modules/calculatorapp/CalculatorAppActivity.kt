package com.github.talkbacktutorial.activities.modules.calculatorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.explorebytouch.ExploreMenuByTouchPart1Fragment
import com.github.talkbacktutorial.databinding.ActivityCalculatorAppModuleBinding
import com.github.talkbacktutorial.databinding.ActivityExploreMenuByTouchModuleBinding

class CalculatorAppActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalculatorAppModuleBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator_app_module)
        supportFragmentManager.commit {
            replace(R.id.frame, CalculatorAppPart1Fragment.newInstance())
        }
    }
}