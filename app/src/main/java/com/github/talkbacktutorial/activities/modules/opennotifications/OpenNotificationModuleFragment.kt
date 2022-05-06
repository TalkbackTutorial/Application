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

    lateinit var mainView: ConstraintLayout

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
        this.ttsEngine = TextToSpeechEngine((activity as OpenNotificationModuleActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.observeUser()
        }
        this.speakIntro()
    }

    private fun observeUser() {
        var viewChangeCounter = 0
        addOnWindowFocusChangeListener {
            speakFeedback(viewChangeCounter)
            viewChangeCounter++;
        }
    }

    private fun speakFeedback(counter: Int) {
        if (counter == 0) {
            ttsEngine.speak("Good job. You have opened the notification drawer. Now try to close by doing the same gesture but" +
                    " this time swipe from bottom to top")
        } else if (counter == 1) {
            ttsEngine.speak("Well done. The notification drawer has been closed. Let's try to open it one more time")
        } else if (counter == 2) {
            ttsEngine.speak("You have done it again. Now close it to move on to new lesson")
        } else if (counter == 3) {
            //move the user to the next lesson/module
        }
    }

    private fun Fragment.addOnWindowFocusChangeListener(callback: (hasFocus: Boolean) -> Unit) =
        view?.viewTreeObserver?.addOnWindowFocusChangeListener(callback)

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
            To do this, simply swipe right then swipe down immediately. Alternatively, you can also do this by 
            using 2 fingers and swipe from top to bottom.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakOutro() {
        val outro = """
            Well done!
            You now know how to open and check your notifications. 
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
