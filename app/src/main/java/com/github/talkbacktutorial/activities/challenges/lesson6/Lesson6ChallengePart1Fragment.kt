package com.github.talkbacktutorial.activities.challenges.lesson6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.challenges.lesson5.Lesson5ChallengeActivity
import com.github.talkbacktutorial.databinding.FragmentLesson5ChallengePart1Binding

class Lesson6ChallengePart1Fragment : Fragment(){
    companion object {
        @JvmStatic
        fun newInstance() = Lesson6ChallengePart1Fragment()
    }

    //TODO: create XML
    private lateinit var binding: FragmentLesson5ChallengePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //TODO: change fragment layout
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson5_challenge_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as Lesson6ChallengeActivity))
            .onFinishedSpeaking(triggerOnce = true) {
            }
        this.speakIntro()
    }


    /**
     * Speaks an intro for the fragment.
     * @author Natalie Law, Sandy Du & Nabeeb Yusuf
     */
    private fun speakIntro() {
        val intro = getString(R.string.lesson6_challenge_fragment1_intro)
        this.ttsEngine.speakOnInitialisation(intro)
    }





}