package com.github.talkbacktutorial.activities.modules.adjustslider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentAdjustSliderModulePart1Binding

class AdjustSliderModulePart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AdjustSliderModulePart1Fragment()
    }

    private lateinit var binding: FragmentAdjustSliderModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    lateinit var menuSlider: SeekBar
    var currentSliderValue: Int = 0
    var maxValue: Int = 100
    var minValue: Int = 0
    var hasReachedMax = false
    var hasReachedMin = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adjust_slider_module_part1, container, false)
        this.menuSlider = this.binding.menuSlider
        currentSliderValue = getSliderValue()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as AdjustSliderModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.menuSlider.visibility = View.VISIBLE
                setSliderHandler()
                speakGoToMax()
            }
        this.speakIntro()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Retrieves the current value of the slider
     * @author Jade Davis
     */
    private fun getSliderValue(): Int {
        return this.menuSlider.progress
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
                binding.seekBarValueTV.setText(i.toString())
                currentSliderValue = i
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                if (currentSliderValue == maxValue && !hasReachedMax) {
                    hasReachedMax = true
                    speakGoToMin()
                } else if (currentSliderValue == minValue && hasReachedMax) {
                    hasReachedMin = true
                    speakOutro()    // TODO: Replace with appropriate action to take to next fragment
                }
            }
        })
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jade Davis
     */
    private fun speakIntro() {
        val intro = """
            Welcome.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     *
     * @author Antony Loose
     */
    private fun speakGoToMax() {
        val goToMax = """
            Go to max.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(goToMax)
    }

    /**
     *
     * @author Antony Loose
     */
    private fun speakGoToMin() {
        val goToMin = """
            Go to min.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(goToMin)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Jade Davis
     */
    private fun speakOutro() {
        val outro = """
            Well done.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(outro)
    }
}