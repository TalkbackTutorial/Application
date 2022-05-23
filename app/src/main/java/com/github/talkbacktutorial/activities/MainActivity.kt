package com.github.talkbacktutorial.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.DebugSettings
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.viewmodels.LessonsViewModel
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgression
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.ActivityMainBinding
import com.github.talkbacktutorial.databinding.LessonCardBinding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer


class MainActivity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine
    private val lessonsModel: LessonsViewModel by viewModels()
    lateinit var mainView: ConstraintLayout
    private lateinit var binding: ActivityMainBinding

    private lateinit var moduleProgressionViewModel: ModuleProgressionViewModel

    init {
        val instance = this
    }

    companion object {
        private val instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lessonsModel = this.lessonsModel
        this.moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        this.ttsEngine = TextToSpeechEngine(this)
        this.mainView = binding.constraintLayout

        if (DebugSettings.wipeDatabase) {
            moduleProgressionViewModel.clearDatabase()
        }

        displayLessons()
    }

    /**
     * Check the status of talkback every time open the main page
     * @author Jason Wu
     */
    override fun onStart() {
        if (!isTalkBackActive() && !DebugSettings.talkbackNotRequired) {
            this.ttsEngine.speakOnInitialisation(getString(R.string.popup_text))
            popup(mainView)
        } else if (!DebugSettings.skipIntroductoryLesson) {
            DebugSettings.skipIntroductoryLesson = true
            lesson0onStart()
        }
        super.onStart()
        displayLessons()
    }

    private fun lesson0onStart() {
        moduleProgressionViewModel.getModuleProgression("lesson0").observe(this) { lesson ->
            if (lesson != null) {
                if (!lesson.completed) {
                    LessonContainer.getLesson(1).startActivity(this)
                }
            }
        }
    }

    /**
     * Detect talkback status
     * @author Jason Wu
     */
    private fun isTalkBackActive(): Boolean {
        val am = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        val isAccessibilityEnabled = am.isEnabled
        val isExploreByTouchEnabled = am.isTouchExplorationEnabled
        return isAccessibilityEnabled && isExploreByTouchEnabled
    }

    /**
     * Popup the window if talkback is inactivated
     * @author Jason Wu
     */
    private fun popup(view: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_window, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = false       // not allow taps outside the popup to dismiss it
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        // fixed trying to show window too early
        view.post { popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0); }

        popupView.findViewById<Button>(R.id.go_setting).setOnClickListener {
            this.ttsEngine.speak(getString(R.string.send_setting), override = true)
            popupWindow.dismiss()
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        popupView.findViewById<Button>(R.id.leave_app).setOnClickListener{
            this.ttsEngine.speak(getString(R.string.exit_application), override = true)
            finishAndRemoveTask()
        }
    }

    /**
     * Pulls the lessonProgression items from the database to determine which lessons should be
     * locked.
     * @author Jade Davis
     */
    private fun displayLessons() {
        moduleProgressionViewModel.getAllModuleProgressions.observe(this) { modules ->
            if (modules.isEmpty()) {
                moduleProgressionViewModel.fillDatabase()
            } else {
                displayLessonCards(modules)
            }
        }
    }

    /**
     * Loads all lessons, and sets each as locked or unlocked depending on the
     * user's progression.
     * @param moduleProgressions All entries in the database, one for each lesson, specifies
     * whether the lesson is completed or not
     * @author Jade Davis
     */
    private fun displayLessonCards(modules: List<ModuleProgression>) {
        binding.lessonLinearLayout.removeAllViews()

        var firstIncompleteLesson = true
        for (lesson in LessonContainer.getAllLessons()){
            var lessonCompleted = false
            var modulesCompleted = 0
            for (module in modules){
                if (module.lessonNum == lesson.sequenceNumeral && module.completed){
                    modulesCompleted++
                }
            }
            if (modulesCompleted == lesson.modules.size){
                lessonCompleted = true
            }
            loadLessonCard(lesson, !(lessonCompleted || firstIncompleteLesson))
            if (!lessonCompleted && firstIncompleteLesson){
                firstIncompleteLesson = false
            }
        }
    }

    /**
     * Loads a card for each lesson, both unlocked and locked.
     * @param lesson Lesson which is to be loaded into a new card
     * @param locked Boolean which determines whether the lesson is locked or not
     * @author Jade Davis
     */
    private fun loadLessonCard(lesson: Lesson, locked: Boolean) {
        val lessonCardBinding: LessonCardBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.lesson_card, binding.lessonLinearLayout, false
        )
        lessonCardBinding.title = lesson.title
        lessonCardBinding.subtitle = lesson.sequenceName
        lessonCardBinding.locked = lesson.isLocked

        if (!locked || DebugSettings.bypassProgressionLocks) {
            lessonCardBinding.lessonCard.setOnClickListener {
                InstanceSingleton.getInstanceSingleton().selectedLessonNumber = lesson.sequenceNumeral
                lesson.startActivity(this)
            }
        } else {
            lessonCardBinding.title = "Locked - " + lesson.title
            lessonCardBinding.lessonTitle.alpha = 0.5f
            lessonCardBinding.lessonSubtitle.alpha = 0.5f
        }

        binding.lessonLinearLayout.addView(lessonCardBinding.lessonCard)
    }
}
