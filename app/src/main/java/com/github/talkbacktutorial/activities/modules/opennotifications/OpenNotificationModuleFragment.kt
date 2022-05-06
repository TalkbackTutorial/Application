package com.github.talkbacktutorial.activities.modules.opennotifications;

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentOpenNotificationBinding


class OpenNotificationModuleFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenNotificationModuleFragment()
    }

    private lateinit var binding: FragmentOpenNotificationBinding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_notification, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine =
            TextToSpeechEngine((activity as OpenNotificationModuleActivity)).onFinishedSpeaking(
                triggerOnce = true
            ) {
                //Trigger this function once the intro is done speaking
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
        var viewChangeCounter = 0
        addOnWindowFocusChangeListener {
            speakFeedback(viewChangeCounter)
            viewChangeCounter++;
        }
    }

    /**
     * This function provides different feedback based on the number of times the user
     * successfully open/close notification
     * @author Vinh Tuan Huynh
     */
    private fun speakFeedback(counter: Int) {
        if (counter == 0) {
            //Once the user open it. Teach them how to close it
            ttsEngine.speak(
                "Good job. You have opened the notification drawer. Now try to close by doing the same gesture but" +
                        " this time start from the bottom to top"
            )
        } else if (counter == 1) {
            //Once the user close it. Teach them how to do it by swiping right then down
            ttsEngine.speak(
                "Well done. The notification drawer has been closed. Let's try to open it one more time." +
                        "But this time, open it by swiping right then down immediately."
            )
        } else if (counter == 2) {
            //Tell the user to close it again
            ttsEngine.speak("Excellent. Now close it to move on to new lesson")
        } else if (counter == 3) {
            //move the user to the next lesson/module
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
            Welcome. In this module, you'll learn how to open your notifications. 
            Notifications are a way to let you know that something new has happened so you don't miss
            anything that might be worth your attention and appears whether you are using the application or not
            There are two way to do this. First, let's open the notification drawer by putting 2 finger on top of the screen then swipe down.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakOutro() {
        val outro = """
            Well done! You now know how to open and check and close your notification drawer. 
            Notification drawer can also be used to perform many kind of shortcut. For example, connect or disconnect wifi or bluetooth.
        """.trimIndent()
        this.ttsEngine.speak(outro)
    }

    private fun finishFragment() {
        this.ttsEngine.onFinishedSpeaking {
        }
        this.speakOutro()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
