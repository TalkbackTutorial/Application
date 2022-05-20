package com.github.talkbacktutorial.activities.modules.adjustslider

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.modules.scroll.ScrollActivity
import com.github.talkbacktutorial.databinding.FragmentAdjustSliderModulePart2Binding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer

class AdjustSliderPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AdjustSliderPart2Fragment()
    }

    private lateinit var binding: FragmentAdjustSliderModulePart2Binding
    lateinit var ttsEngine: TextToSpeechEngine

    lateinit var mainView: ConstraintLayout
    lateinit var menuSlider: SeekBar

    // Slider vars
    val maxValue: Int = 100
    val minValue: Int = 0
    var hasReachedMax = false
    var hasReachedMin = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adjust_slider_module_part2, container, false)
        this.menuSlider = this.binding.menuSlider
        this.mainView = this.binding.adjustSliderModule2Layout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as AdjustSliderActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.mainView.visibility = View.VISIBLE
                setSliderHandler()
                // this is used to execute code before talkback executes on a slider
                this.mainView.accessibilityDelegate = AdjustSliderDelegate(activity as AdjustSliderActivity)
            }
        speakIntro()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Sets the onChange event handler for the seekBar. Updates the class attribute which stores
     * the current progress of the menu bar and handles the logic for knowing when the max and min
     * have been reached, and when to allow the next portion of the module to continue.
     * @author Jade Davis
     */
    private fun setSliderHandler() {
        this.menuSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (i == maxValue) {
                    hasReachedMax = true
                    speakGoToMin()
                } else if (i == minValue && hasReachedMax) {
                    hasReachedMin = true
                    finishLesson()
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })
    }

    /**
     * Speaks an intro for the fragment.
     * @author Antony Loose
     */
    private fun speakIntro() {
        val intro = """
            Welcome to the next part of the module.
            Another way of adjusting sliders is to double tap then drag.
            To start you will try increasing the sliders value.
            To increase a slider double tap and hold the slider then drag right.
            To continue increase the slider value to 100%.
            Note that you will have to use explore by touch to find the slider.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Tells user to go to min slider value
     * @author Antony Loose
     */
    private fun speakGoToMin() {
        val goToMin = """
            Now you will try decreasing the sliders value.
            To decrease a slider double tap and hold then drag left.
            To continue decrease the slider value to 0%.
        """.trimIndent()
        this.ttsEngine.speak(goToMin)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Antony Loose
     */
    private fun speakOutro() {
        val outro = """
            Well done! now you know how to adjust sliders by dragging.
            This gesture can be used for any sliders, but will be most useful for adjusting video and song times.
        """.trimIndent()
        this.ttsEngine.speak(outro)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Antony Loose
     */
    private fun finishLesson() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as AdjustSliderActivity), LessonActivity::class.java)
            val currentLesson : Lesson = LessonContainer.getAllLessons()[1]
            intent.putExtra(Lesson.INTENT_KEY, currentLesson.id.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        speakOutro()
    }
}
