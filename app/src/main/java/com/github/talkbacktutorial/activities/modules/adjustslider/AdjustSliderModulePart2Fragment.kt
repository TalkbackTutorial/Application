package com.github.talkbacktutorial.activities.modules.adjustslider

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
    private lateinit var ttsEngine: TextToSpeechEngine

    lateinit var mainView: ConstraintLayout
    lateinit var menuSlider: SeekBar
    lateinit var sliderBelowTV: TextView
    lateinit var sliderAboveTV: TextView
    lateinit var sliderRightTV: TextView
    lateinit var sliderLeftTV: TextView

    var currentSliderValue: Int = 0
    var maxValue: Int = 100
    var minValue: Int = 0
    var hasReachedMax = false
    var hasReachedMin = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adjust_slider_module_part2, container, false)
        this.menuSlider = this.binding.menuSlider
        this.sliderBelowTV = this.binding.sliderBelowTV
        this.sliderAboveTV = this.binding.sliderAboveTV
        this.sliderRightTV = this.binding.sliderRightTV
        this.sliderLeftTV = this.binding.sliderLeftTV
        this.mainView = this.binding.adjustSliderModule2Layout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as AdjustSliderModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.menuSlider.visibility = View.VISIBLE
                this.sliderBelowTV.visibility = View.VISIBLE
                this.sliderAboveTV.visibility = View.VISIBLE
                this.sliderRightTV.visibility = View.VISIBLE
                this.sliderLeftTV.visibility = View.VISIBLE
                // this is used to execute code before talkback executes on a slider
                this.mainView.accessibilityDelegate = AdjustSliderDelegate(maxValue, minValue)

                setSliderHandler()
            }
        this.speakIntro()
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
                currentSliderValue = i
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })
    }

    /**
     * Tells user to go to min slider value
     * @author Antony Loose
     */
    private fun speakGoToMin() {
        val goToMin = """
            double tap and hold slider then move finger left to go to min
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(goToMin)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Antony Loose
     */
    private fun speakIntro() {

        val intro = """
            Another way of adjusting sliders is to double tap then drag left to decrease and right to increase.
            Use explore by touch to find the slider then double tap and hold for 1 second to select
            finally move finger right until slider is 100% then lift finger.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Antony Loose
     */
    private fun speakOutro() {
        val outro = """
            Well done! now you know how to adjust sliders by dragging
            This gesture can be used for any sliders but will be most useful for adjusting video and song times.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(outro)
    }

    private fun finishedLesson(){
        // TODO
    }
}
