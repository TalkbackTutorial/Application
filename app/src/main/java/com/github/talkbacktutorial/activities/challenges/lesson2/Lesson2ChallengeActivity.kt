package com.github.talkbacktutorial.activities.challenges.lesson2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.adjustreadingcontrols.AdjustReadingControlsPart1Fragment
import com.github.talkbacktutorial.databinding.ChallengeLayoutBinding

class Lesson2ChallengeActivity : AppCompatActivity() {

    lateinit var binding: ChallengeLayoutBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.challenge_layout)

        supportFragmentManager.commit {
            replace(R.id.frame, Lesson2ChallengePart1Fragment())
        }
    }
}