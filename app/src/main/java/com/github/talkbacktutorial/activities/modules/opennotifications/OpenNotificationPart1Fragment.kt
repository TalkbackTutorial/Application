package com.github.talkbacktutorial.activities.modules.opennotifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
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
            Timer().schedule(3000) {
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
            Timer().schedule(7000) {
                ttsEngine.speak(getString(R.string.open_notifications_feedback_1))
            }
        } else if (counter == 1) {
            // once the user close the panel . Teach them how to do it by swiping right then down
            ttsEngine.speak(getString(R.string.open_notifications_feedback_2))
        } else if (counter == 2) {
            // tell the user to close the panel after it is opened again
            Timer().schedule(7000) {
                ttsEngine.speak(getString(R.string.open_notifications_feedback_3))
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
        val intro = getString(R.string.open_notifications_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakOutro() {
        val outro = getString(R.string.open_notifications_outro).trimIndent()
        this.ttsEngine.speak(outro)
    }

    /**
     * To do when finish the lesson
     * @author Vinh Tuan Huynh
     */
    private fun finishLesson() {
        (activity as OpenNotificationActivity).runOnUiThread {
            updateModule()
        }

        removeOnWindowFocusChangeListener {}
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.finish()
        }

        this.speakOutro()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}
