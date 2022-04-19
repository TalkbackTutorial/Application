package com.github.talkbacktutorial.activities.lesson2.selectsettingmodule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Activity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part1Fragment
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part2Fragment
import com.github.talkbacktutorial.activities.lesson2.Lesson2Module1P2Fragment
import com.github.talkbacktutorial.activities.modules.ExploreMenuByTouchActivity
import com.github.talkbacktutorial.activities.modules.SelectSettingModuleActivity
import com.github.talkbacktutorial.databinding.ActivityLesson2Module1P2FragmentBinding
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentLesson2SelectsettingP1FragmentBinding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class SelectSettingPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SelectSettingPart1Fragment()
    }

    private lateinit var binding: FragmentLesson2SelectsettingP1FragmentBinding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson2_selectsetting_p1_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as SelectSettingModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {

            }
        this.speakIntro()
    }


    /**
     * Speaks an intro for the fragment.
     * @author Antony Loose + Jade Davis
     */
    private fun speakIntro() {
        val intro = """
            Test
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

}