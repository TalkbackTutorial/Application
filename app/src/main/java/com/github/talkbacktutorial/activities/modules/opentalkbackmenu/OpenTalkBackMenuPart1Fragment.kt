package com.github.talkbacktutorial.activities.modules.opentalkbackmenu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentOpenTalkbackMenuModulePart1Binding
import java.util.*
import kotlin.concurrent.schedule

class OpenTalkBackMenuPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenTalkBackMenuPart1Fragment()
    }

    private lateinit var binding: FragmentOpenTalkbackMenuModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private var viewChangeCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_open_talkback_menu_module_part_1,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine =
            TextToSpeechEngine((activity as OpenTalkbackMenuActivity)).onFinishedSpeaking(
                triggerOnce = true
            ) {
                // Trigger this function once the intro is done speaking
                this.observeUser()
            }
        this.speakIntro()
    }

    /**
     * This function observe and count the view changes
     * @author Vinh Tuan Huynh
     */
    private fun observeUser() {
        // Simple listener detecting a change in window focus
        view?.viewTreeObserver?.addOnWindowFocusChangeListener { _ ->
            Timer().schedule(3500) {
                speakFeedback(viewChangeCounter)
                viewChangeCounter++
            }
        }
    }

    private fun speakFeedback(counter: Int) {
        if (counter == 0) {
            this.ttsEngine.speak(
                "Great job. You have opened the Talkback menu. Feel free to navigate around to see what you can do." +
                        "Once you are done, go to the bottom of the menu and exit."
            )
        } else {
            finishLesson()
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakIntro() {
        val intro = """
            Welcome. In this module, you'll learn how to open Talkback Menu.
            Talkback menu let you use commands to read, edit text, control speech output, change Talkback setting, and so on.
            To do this, use one finger to swipe up, or down then swipe right immediately. 
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakOutro() {
        val outro = """
            Well done! You now know how to open and close Talkback Menu. 
            Once again, to do this you have to swipe up, or down then swipe right.
            You have completed the lesson. 
        """.trimIndent()
        this.ttsEngine.speak(outro)
    }

    /**
     * To do when finish the lesson
     * @author Vinh Tuan Huynh
     */
    private fun finishLesson() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent =
                Intent((activity as OpenTalkbackMenuActivity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.speakOutro()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}