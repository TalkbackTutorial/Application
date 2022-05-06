package com.github.talkbacktutorial.activities.modules.opennotifications;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentOpenNotificationBinding

class OpenNotificationModuleFragment : Fragment() {
    private lateinit var binding: FragmentOpenNotificationBinding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_open_notification, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as OpenNotificationModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {
            }
        this.speakIntro()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

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
}
