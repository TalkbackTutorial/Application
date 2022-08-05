package com.github.talkbacktutorial.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.github.talkbacktutorial.activities.gamemode.GameModeAcitivity
import com.github.talkbacktutorial.activities.sandboxmode.SandboxModeActivity
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
            lesson1onStart()
        }
        super.onStart()
        displayCards()
    }

    /**
     * Starts the app on Lesson1 when user opens app for the first time
     * @author Emmanuel Chu
     */
    private fun lesson1onStart() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("lesson1Completed", Context.MODE_PRIVATE)
        val completedBoolean = sharedPreferences.getBoolean("completed", false)
        if (!completedBoolean) {
            LessonContainer.getLesson(1).startActivity(this)
        }
        setLesson1Completed()
    }

    /**
     * Marks Lesson 1 as completed in Shared Preferences
     * @author Emmanuel Chu
     */
    private fun setLesson1Completed() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("lesson1Completed", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("completed", true)
        }.apply()
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
    private fun displayCards() {
        moduleProgressionViewModel.getAllModuleProgressions.observe(this) { modules ->
            if (modules.isEmpty()) {
                moduleProgressionViewModel.fillDatabase()
            } else {
                displayLessonCards(modules)
                loadSandboxCard()
                loadGameModeCard()
            }
        }
    }

    /**
     * Loads all lessons, and sets each as locked or unlocked depending on the
     * user's progression.
     * @param moduleProgressions All entries in the database, one for each lesson, specifies
     * whether the lesson is completed or not
     * @author Antony Loose, Jade Davis
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

    /**
     * Load sandbox card
     * @author Antony Loose
     */
    private fun loadSandboxCard(){
        val openSandbox: () -> Unit = {
            val intent = Intent(this, SandboxModeActivity::class.java)
            this.startActivity(intent)
        }
        loadCard("Experiment With Gestures", "Sandbox Mode", openSandbox)
    }

    /**
     * Load game mode card
     * @author Antony Loose
     */
    private fun loadGameModeCard(){
        val openGameMode: () -> Unit = {
            val intent = Intent(this, GameModeAcitivity::class.java)
            this.startActivity(intent)
        }
        // TODO: come up with name and subtitle for game
        loadCard("Consolidate Learnt Gestures", "Minigame", openGameMode)
    }

    /**
     * A general function for loading simple cards
     * @param title the title of the card
     * @param subtitle the subtitle of the card
     * @param onClick a lambda function that is called when the card is clicked
     * @author Antony Loose
     */
    private fun loadCard(title: String, subtitle: String, onClick: () -> Unit){
        val cardBinding: LessonCardBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.lesson_card, binding.lessonLinearLayout, false
        )
        cardBinding.title = title
        cardBinding.subtitle = subtitle
        cardBinding.locked = false

        cardBinding.lessonCard.setOnClickListener {
            // navigate to game mode
            onClick()
        }

        binding.lessonLinearLayout.addView(cardBinding.lessonCard)
    }
}
