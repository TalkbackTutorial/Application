package com.github.talkbacktutorial.activities.modules.openrecentapps

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
import com.github.talkbacktutorial.databinding.FragmentOpenRecentAppsPart2Binding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer
import java.util.*
import kotlin.concurrent.schedule

class OpenRecentAppsPart2Fragment : Fragment() {
    private lateinit var binding: FragmentOpenRecentAppsPart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_open_recent_apps_part2,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as OpenRecentAppsActivity))
        this.speakIntro()
        // Simple listener detecting a change in window focus
        view.viewTreeObserver?.addOnWindowFocusChangeListener { _ ->
            count++
            // If the count is equal to 1, the app must have lost focus and re-gained focus
            // Count changed to one here as changing fragments does not count as a focus change
            if (count == 1) {
                finishLesson()
            }
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jai Clapp
     */
    private fun speakIntro() {
        val intro = getString(R.string.open_recent_apps_part2_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Completes the module
     * @author Jai Clapp
     */
    private fun finishLesson() {
        updateModule()
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as OpenRecentAppsActivity), LessonActivity::class.java)
            val currentLesson : Lesson = LessonContainer.getAllLessons()[4]
            intent.putExtra(Lesson.INTENT_KEY, currentLesson.id.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        // Delay to prevent bug where the ttsEngine is repeated.
        Timer().schedule(2000) {
            val outro = getString(R.string.open_recent_apps_part2_outro).trimIndent()
            ttsEngine.speakOnInitialisation(outro)
        }
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
