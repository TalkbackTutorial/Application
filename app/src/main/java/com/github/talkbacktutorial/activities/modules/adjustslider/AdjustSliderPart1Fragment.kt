package com.github.talkbacktutorial.activities.modules.adjustslider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentAdjustSliderModulePart1Binding

class AdjustSliderPart1Fragment() : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AdjustSliderPart1Fragment()
    }

    private lateinit var binding: FragmentAdjustSliderModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    lateinit var mainView: ConstraintLayout
    lateinit var menuSlider: SeekBar
    val maxValue: Int = 100
    val minValue: Int = 50
    var hasReachedMax = false
    var hasReachedMin = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adjust_slider_module_part1, container, false)
        this.menuSlider = this.binding.menuSlider
        this.mainView = this.binding.adjustSliderModule1Layout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as AdjustSliderActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.menuSlider.visibility = View.VISIBLE
                // this is used to execute code before talkback executes on a slider
                this.mainView.accessibilityDelegate = AdjustSliderDelegate(activity as AdjustSliderActivity)
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
                if (i == maxValue && !hasReachedMax) {
                    hasReachedMax = true
                    speakGoToMin()
                } else if (i == minValue && hasReachedMax) {
                    hasReachedMin = true
                    finishFragment()
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) { }

            override fun onStopTrackingTouch(seek: SeekBar) { }
        })
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jade Davis
     */
    private fun speakIntro() {
        val intro = getString(R.string.adjust_slider_part1_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Tells user to go to min slider value
     * @author Antony Loose
     */
    private fun speakGoToMin() {
        val goToMin = getString(R.string.adjust_slider_part1_go_to_min).trimIndent()
        this.ttsEngine.speak(goToMin)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Jade Davis
     */
    private fun speakOutro() {
        val outro = getString(R.string.adjust_slider_part1_outro).trimIndent()
        this.ttsEngine.speak(outro)
    }

    private fun finishFragment() {
        this.ttsEngine.onFinishedSpeaking {
            parentFragmentManager.commit {
                replace(
                    this@AdjustSliderPart1Fragment.id,
                    AdjustSliderPart2Fragment.newInstance()
                )
                addToBackStack(getString(R.string.adjust_slider_part2_backstack))
            }
        }
        this.speakOutro()
    }
}
