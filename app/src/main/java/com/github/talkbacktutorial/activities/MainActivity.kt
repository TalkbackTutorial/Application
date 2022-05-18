package com.github.talkbacktutorial.activities

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
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.viewmodels.LessonsViewModel
import com.github.talkbacktutorial.database.LessonProgression
import com.github.talkbacktutorial.database.LessonProgressionViewModel
import com.github.talkbacktutorial.databinding.ActivityMainBinding
import com.github.talkbacktutorial.databinding.LessonCardBinding
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

        // Show all lessons in LessonContainer
        // TODO: database integration, only display complete lessons and the next lesson
        displayAvailableLessons()
    }

    /**
     * Check the status of talkback every time open the main page
     * @author Jason Wu
     */
    override fun onStart() {
        if (!isTalkBackActive()) {
            this.ttsEngine.speakOnInitialisation(getString(R.string.popup_text))
            popup(mainView)
        }
        super.onStart()
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

    private fun displayAvailableLessons() {
        lessonProgressionViewModel = ViewModelProvider(this).get(LessonProgressionViewModel::class.java)
        lessonProgressionViewModel.getAllLessonProgressions.observe(this) {lessons ->
            if (lessons.isEmpty()) {
                lessonProgressionViewModel.fillDatabase()
            } else {
                displayLessons(lessons)
            }
        }
    }

    private fun displayLessons(lessonProgressions: List<LessonProgression>) {
        var foundIncompleteLesson = false
        var lessonCount = 0
        val lessons = LessonContainer.getAllLessons()

        while (lessonCount < lessons.size - 1) {
            if (!lessonProgressions[lessonCount].completed && !foundIncompleteLesson) {
                foundIncompleteLesson = true
                continue
            }

            val lesson = lessons[lessonCount]

            val lessonCardBinding: LessonCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.lesson_card, binding.lessonLinearLayout, false
            )
            lessonCardBinding.title = lesson.title
            lessonCardBinding.subtitle = lesson.sequenceName
            lessonCardBinding.locked = lesson.isLocked
            lessonCardBinding.lessonCard.setOnClickListener {
                lesson.startActivity(this)
            }
            binding.lessonLinearLayout.addView(lessonCardBinding.lessonCard)

            if (foundIncompleteLesson) {
                break
            }

            lessonCount++
        }
    }
}
