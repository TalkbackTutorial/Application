package com.github.talkbacktutorial.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangeListener
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangeManager
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangePage
import com.github.talkbacktutorial.activities.challenges.lesson5.Lesson5ChallengePart2Fragment
import com.github.talkbacktutorial.activities.gamemode.GameModeActivity
import com.github.talkbacktutorial.activities.sandboxmode.SandboxModeActivity
import com.github.talkbacktutorial.activities.viewmodels.LessonsViewModel
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgression
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.database.gamemode.GameModeViewModel
import com.github.talkbacktutorial.databinding.ActivityMainBinding
import com.github.talkbacktutorial.databinding.LessonCardBinding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer


class MainActivity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine
    private val lessonsModel: LessonsViewModel by viewModels()
    private lateinit var mainView: ConstraintLayout
    private lateinit var binding: ActivityMainBinding
    private lateinit var moduleProgressionViewModel: ModuleProgressionViewModel
    private lateinit var gameModeDbController: GameModeViewModel
    private var popupWindow: PopupWindow = PopupWindow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lessonsModel = this.lessonsModel
        this.moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        this.gameModeDbController = ViewModelProvider(this).get(GameModeViewModel::class.java)
        this.ttsEngine = TextToSpeechEngine(this)
        this.mainView = binding.constraintLayout

        // fill the game mode data base if empty
        fillGameModeDb()
        if (DebugSettings.wipeDatabase) {
            moduleProgressionViewModel.clearDatabase()
            gameModeDbController.clearDatabase()
        }

        if (!DebugSettings.talkbackNotRequired) {
            AccessibilityChangeManager.setListener(
                AccessibilityChangeListener(
                    talkbackOnCallback = {
                        popupWindow.dismiss()
                    },
                    talkbackOffCallback = {
                        popup(mainView)
                    },
                    associatedPage = AccessibilityChangePage.MAIN
                )
            )
        }
    }

    /**
     * Check the status of talkback every time open the main page
     * @author Jason Wu
     */
    override fun onStart() {
        if (!isTalkBackActive() && !DebugSettings.talkbackNotRequired) {
            popup(mainView)
        } else if (!DebugSettings.skipIntroductoryLesson) {
            lesson1onStart()
        }
        super.onStart()
        displayCards()
    }

    override fun onResume() {
        AccessibilityChangeManager.setPage(AccessibilityChangePage.MAIN)
        // Check if user back from notification - for lesson5 challenge
        if (intent.hasExtra(Lesson5ChallengePart2Fragment.LESSON5_COMPLETED)) {
            Handler(Looper.getMainLooper()).postDelayed({
                this.ttsEngine.speak(getString(R.string.lesson5_challenge_complete))
            }, 4000)    // Avoid conflicts with tts
            intent.removeExtra(Lesson5ChallengePart2Fragment.LESSON5_COMPLETED)
        }
        super.onResume()
    }

    override fun onStop() {
        AccessibilityChangeManager.resetPage(AccessibilityChangePage.MAIN)
        this.popupWindow.dismiss()
        super.onStop()
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
        if (this.popupWindow.isShowing) {
            // We never want a situation where popup windows overlay each other
            this.popupWindow.dismiss()
        }
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_window, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = false       // not allow taps outside the popup to dismiss it
        popupWindow = PopupWindow(popupView, width, height, focusable)

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
            ttsEngine.shutDown()    // Avoid speak popup message after exit
        }

        Handler(Looper.getMainLooper()).postDelayed({
            this.ttsEngine.speakOnInitialisation(getString(R.string.popup_text))
        }, 300)     // Set time delay to avoid tts to be killed by "talkback off"
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
     * @param modules All entries in the database, one for each lesson, specifies
     * whether the lesson is completed or not
     * @author Antony Loose, Jade Davis
     */
    private fun displayLessonCards(modules: List<ModuleProgression>) {
        binding.lessonLinearLayout.removeAllViews()

        var firstIncompleteLesson = true
        var lastavailableLesson : Lesson? = null
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

            if (lessonCompleted || firstIncompleteLesson) {
                lastavailableLesson = lesson
            }

            loadLessonCard(lesson, !(lessonCompleted || firstIncompleteLesson), lastavailableLesson)
            if (!lessonCompleted && firstIncompleteLesson){
                firstIncompleteLesson = false
            }
        }
    }

    /**
     * Loads a card for each lesson, both unlocked and locked.
     * @param lesson Lesson which is to be loaded into a new card
     * @param locked Boolean which determines whether the lesson is locked or not
     * @param lastAvailableLesson last available lesson for the user, used for accessibility hinting purposes
     * @author Jade Davis, Mingxuan Fu
     */
    private fun loadLessonCard(lesson: Lesson, locked: Boolean, lastAvailableLesson: Lesson? = null) {
        val lessonCardBinding: LessonCardBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.lesson_card, binding.lessonLinearLayout, false
        )
        lessonCardBinding.title = lesson.title
        lessonCardBinding.subtitle = lesson.sequenceName
        lessonCardBinding.locked = lesson.isLocked

        if (!locked || DebugSettings.bypassProgressionLocks) {
            lessonCardBinding.lessonCard.isClickable = true
            lessonCardBinding.lessonCard.setOnClickListener {
                InstanceSingleton.getInstanceSingleton().selectedLessonNumber = lesson.sequenceNumeral
                lesson.startActivity(this)
            }
        } else {
            lessonCardBinding.lessonCard.isClickable = false
            lessonCardBinding.lessonCard.contentDescription =
                "Lesson ${lesson.sequenceNumeral} is currently locked${if (lastAvailableLesson != null) ", please swipe left and complete Lesson ${lastAvailableLesson.sequenceNumeral} first." else "."}"
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
        loadCard(getString(R.string.sandbox_mode_subtitle), getString(R.string.sandbox_mode_title), openSandbox)
    }

    /**
     * Load game mode card
     * @author Antony Loose
     */
    private fun loadGameModeCard(){
        val openGameMode: () -> Unit = {
            val intent = Intent(this, GameModeActivity::class.java)
            this.startActivity(intent)
        }
        loadCard(getString(R.string.game_mode_subtitle), getString(R.string.game_mode_title), openGameMode)
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

    private fun fillGameModeDb(){
        gameModeDbController.getAllHighScores.observe(this) { highScores ->
            if (highScores == null || highScores.isEmpty()){
                gameModeDbController.fillDatabase()
            }
        }
    }
}
