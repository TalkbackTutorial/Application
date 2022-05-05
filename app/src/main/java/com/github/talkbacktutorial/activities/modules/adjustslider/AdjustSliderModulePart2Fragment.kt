package com.github.talkbacktutorial.activities.modules.adjustslider

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.modules.scrolling.ScrollingModuleActivity
import com.github.talkbacktutorial.databinding.FragmentAdjustSliderModulePart2Binding

/**
 * This fragment teaches how to adjust a slider by double tapping then dragging
 * @author Antony Loose
 */
class AdjustSliderModulePart2Fragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = AdjustSliderModulePart2Fragment()
    }

    private lateinit var binding: FragmentAdjustSliderModulePart2Binding
    lateinit var ttsEngine: TextToSpeechEngine

    lateinit var mainView: ConstraintLayout
    lateinit var menuSlider: SeekBar

    // TTS text to speak
    val goToMin = """move your finger left to adjust slider value to 0%""".trimIndent()
    val outro = """
        Well done! now you know how to adjust sliders by dragging
        This gesture can be used for any sliders but will be most useful for adjusting video and song times.
    """.trimIndent()
    val intro = """
            Another way of adjusting sliders is to double tap then drag left to decrease and drag right to increase.
            Use explore by touch to find the slider then double tap and hold for 1 second to select
            finally move your finger right until slider value is 100%.
        """.trimIndent()

    // Slider vars
    val maxValue: Int = 100
    val minValue: Int = 0
    var hasReachedMax = false
    var hasReachedMin = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adjust_slider_module_part2, container, false)
        this.menuSlider = this.binding.menuSlider
        this.mainView = this.binding.adjustSliderModule2Layout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as AdjustSliderModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.mainView.visibility = View.VISIBLE
                setSliderHandler()
                // this is used to execute code before talkback executes on a slider
                this.mainView.accessibilityDelegate = AdjustSliderDelegate(activity as AdjustSliderModuleActivity)
            }
        this.ttsEngine.speakOnInitialisation(intro)
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
                    this@AdjustSliderModulePart2Fragment.ttsEngine.speak(goToMin, true)
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
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Antony Loose
     */
    private fun finishLesson() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as AdjustSliderModuleActivity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.ttsEngine.speak(outro, override = true)
    }
}
