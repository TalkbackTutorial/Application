package com.github.talkbacktutorial.activities.modules.opennotifications

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
import com.github.talkbacktutorial.databinding.FragmentOpenNotificationModulePart1Binding
import java.util.*
import kotlin.concurrent.schedule

class OpenNotificationPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenNotificationPart1Fragment()
    }

    private lateinit var binding: FragmentOpenNotificationModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    private var viewChangeCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_notification_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine =
            TextToSpeechEngine((activity as OpenNotificationActivity)).onFinishedSpeaking(
                triggerOnce = true
            ) {
                // Trigger this function once the intro is done speaking
                this.observeUser()
            }
        this.speakIntro()
    }

    /**
     * This function observe and count the view changes i.e., everytime the user open or close the notification and
     * tell them what to do next
     * @author Vinh Tuan Huynh
     */
    private fun observeUser() {
        addOnWindowFocusChangeListener {
            Timer().schedule(1000) {
                speakFeedback(viewChangeCounter)
                viewChangeCounter++
            }
        }
    }

    /**
     * This function provides different feedback based on the number of times the user
     * successfully open/close notification
     * @author Vinh Tuan Huynh
     */
    private fun speakFeedback(counter: Int) {
        if (counter == 0) {
            // once the user open noti panel. Teach them how to close it. Since Talkback will be talking a lot here and there is no way to stop it,
            // extend the timer so tts engine can talk after Talkback is done talking.
            // Note that the delay time does not have to be exact it can be anything as long as it is after Talkback start ranting :)
            Timer().schedule(5000) {
                ttsEngine.speak(
                    "Good job. You have opened the notification panel. Now try to close it by doing the same gesture but" +
                        " this time start from the bottom to top"
                )
            }
        } else if (counter == 1) {
            // once the user close the panel . Teach them how to do it by swiping right then down
            ttsEngine.speak(
                "Well done. The notification panel has been closed. Let's try to open it one more time" +
                    "but this time, open it by swiping right, then down immediately."
            )
        } else if (counter == 2) {
            // tell the user to close the panel after it is opened again
            Timer().schedule(5000) {
                ttsEngine.speak("Excellent. Now close it again by swiping from the bottom with 2 finger")
            }
        } else if (counter == 3) {
            // move the user to the next lesson/module
            viewChangeCounter = 0
            finishLesson()
        }
    }

    /**
     * This function adds a window focus change listener. Basically, this listener will call the callback func everytime
     * we do something that alternate the view (window focus change) e.g., open the notification drawer or any kind of drawer.
     * @author: Vinh Tuan Huynh
     */
    private fun Fragment.addOnWindowFocusChangeListener(callback: (hasFocus: Boolean) -> Unit) =
        view?.viewTreeObserver?.addOnWindowFocusChangeListener(callback)

    /**
     * This function delete the listener added above
     * @author: Vinh Tuan Huynh
     */
    private fun Fragment.removeOnWindowFocusChangeListener(callback: (hasFocus: Boolean) -> Unit) =
        view?.viewTreeObserver?.removeOnWindowFocusChangeListener(callback)

    /**
     * Speaks an intro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakIntro() {
        val intro = """
            Welcome. In this module, you'll learn how to open your notification panel. 
            Notifications are a way to let you know that something new has happened so you don't miss anything that might be worth your attention and appears whether you are using the application or not
            There are two way to do this. First, let's open the notification panel by putting 2 finger on top of the screen then swipe down.
            Once you opened the notification panel, Talkback will start taking over for a few second. 
            Please wait until Talkback is done then follow the next instruction. Good luck.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakOutro() {
        val outro = """
            Well done! You now know how to open and check and close your notification panel. 
            Notification panel can also be used to perform many kind of shortcut. For example, connect or disconnect wifi or bluetooth.
            You have completed the lesson. We will send you back.
        """.trimIndent()
        this.ttsEngine.speak(outro)
    }

    /**
     * To do when finish the lesson
     * @author Vinh Tuan Huynh
     */
    private fun finishLesson() {
        removeOnWindowFocusChangeListener {}
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.onBackPressed()
        }
        this.speakOutro()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
