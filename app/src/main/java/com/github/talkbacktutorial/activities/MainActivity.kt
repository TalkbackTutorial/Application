package com.github.talkbacktutorial.activities

import android.content.Intent
import android.os.Bundle
import android.os.Debug
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import com.github.talkbacktutorial.DebugSettings
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.lesson1.Lesson1Activity
import com.github.talkbacktutorial.activities.viewmodels.LessonsViewModel
import com.github.talkbacktutorial.database.LessonProgression
import com.github.talkbacktutorial.database.LessonProgressionRepository
import com.github.talkbacktutorial.database.LessonProgressionViewModel
import com.github.talkbacktutorial.databinding.ActivityMainBinding
import com.github.talkbacktutorial.databinding.LessonCardBinding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.Lesson1
import com.github.talkbacktutorial.lessons.LessonContainer


class MainActivity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine
    private val lessonsModel: LessonsViewModel by viewModels()
    lateinit var mainView: ConstraintLayout
    private lateinit var binding: ActivityMainBinding

    private lateinit var lessonProgressionViewModel: LessonProgressionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lessonsModel = this.lessonsModel
        this.ttsEngine = TextToSpeechEngine(this)
        this.mainView = binding.constraintLayout

        lesson0onStart()

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
        }
        super.onStart()
    }

    private fun lesson0onStart() {
        lessonProgressionViewModel = ViewModelProvider(this).get(LessonProgressionViewModel::class.java)

        lessonProgressionViewModel.getLessonProgression(1).observe(this) { lesson ->
            if (!lesson.completed) {
                LessonContainer.getLesson(1).startActivity(this)
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
        lessonProgressionViewModel = ViewModelProvider(this).get(LessonProgressionViewModel::class.java)
        lessonProgressionViewModel.getAllLessonProgressions.observe(this) {lessons ->
            if (lessons.isEmpty()) {
                lessonProgressionViewModel.fillDatabase()
            } else {
                displayLessons(lessons)
            }
        }
    }

    /**
     * Loads all lessons, and sets each as locked or unlocked depending on the
     * user's progression.
     * @param lessonProgressions All entries in the database, one for each lesson, specifies
     * whether the lesson is completed or not
     * @author Jade Davis
     */
    private fun displayLessons(lessonProgressions: List<LessonProgression>) {
        var lessonCount = 0
        val lessons = LessonContainer.getAllLessons()

        do {
            loadLessonCard(lessons[lessonCount], locked = false)
            lessonCount++
        } while (lessonProgressions[lessonCount].completed)

        for (i in lessonCount until lessons.size) {
            loadLessonCard(lessons[i], locked = true)
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
