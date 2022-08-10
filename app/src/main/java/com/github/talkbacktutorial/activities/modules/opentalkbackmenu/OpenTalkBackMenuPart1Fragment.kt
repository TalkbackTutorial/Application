package com.github.talkbacktutorial.activities.modules.opentalkbackmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentOpenTalkbackMenuModulePart1Binding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer
import java.util.*
import kotlin.concurrent.schedule

class OpenTalkBackMenuPart1Fragment : Fragment() {

    private lateinit var binding: FragmentOpenTalkbackMenuModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

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
        this.ttsEngine = TextToSpeechEngine((activity as OpenTalkbackMenuActivity))
        this.speakIntro()
        var viewChangeCounter = 0
        //adds a window focus change listener. Basically, this listener will call the callback func everytime
        //we do something that alternate the view (window focus change) e.g., open the notification shade
        view.viewTreeObserver?.addOnWindowFocusChangeListener { _ ->
            if (viewChangeCounter > 1) {
                finishLesson()
            }
            Timer().schedule(4000) {
                speakFeedback(viewChangeCounter)
                viewChangeCounter++
            }
        }
    }

    /**
     * This function give feedback to guide the user what to do next
     */
    private fun speakFeedback(counter: Int) {
        if (counter == 1) {
            ttsEngine.speak(getString(R.string.open_talkback_menu_feedback))
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakIntro() {
        val intro = getString(R.string.open_talkback_menu_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Vinh Tuan Huynh
     */
    private fun speakOutro() {
        val outro = getString(R.string.open_talkback_menu_outro).trimIndent()
        this.ttsEngine.speak(outro)
    }

    /**
     * To do when finish the lesson
     * @author Vinh Tuan Huynh
     */
    private fun finishLesson() {
        updateModule()
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as OpenTalkbackMenuActivity), LessonActivity::class.java)
            val currentLesson: Lesson = LessonContainer.getAllLessons()[4]
            intent.putExtra(Lesson.INTENT_KEY, currentLesson.id.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        Timer().schedule(2000) {
            speakOutro()
        }
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule() {
        val moduleProgressionViewModel =
            ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}