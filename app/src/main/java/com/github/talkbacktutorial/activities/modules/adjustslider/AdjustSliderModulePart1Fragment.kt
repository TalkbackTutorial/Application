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
import com.github.talkbacktutorial.activities.modules.scrolling.ScrollingModulePart2Fragment
import com.github.talkbacktutorial.databinding.FragmentAdjustSliderModulePart1Binding

class AdjustSliderModulePart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AdjustSliderModulePart1Fragment()
    }

    private lateinit var binding: FragmentAdjustSliderModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    lateinit var mainView: ConstraintLayout
    lateinit var menuSlider: SeekBar
    var currentSliderValue: Int = 0
    var maxValue: Int = 100
    var minValue: Int = 50
    var hasReachedMax = false
    var hasReachedMin = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adjust_slider_module_part1, container, false)
        this.menuSlider = this.binding.menuSlider
        this.mainView = this.binding.adjustSliderModule1Layout
        currentSliderValue = getSliderValue()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as AdjustSliderModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.menuSlider.visibility = View.VISIBLE
                // this is used to execute code before talkback executes on a slider
                this.mainView.accessibilityDelegate = AdjustSliderDelegate(activity as AdjustSliderModuleActivity)
                setSliderHandler()
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
                if (i == maxValue && !hasReachedMax) {
                    hasReachedMax = true
                    speakGoToMin()
                } else if (i == minValue && hasReachedMax) {
                    hasReachedMin = true
                    finishFragment()
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
     * @author Jade Davis
     */
    private fun speakIntro() {
        // TODO: better intro
        val intro = """
            Welcome to the slider lesson. You can increase the slider value by swiping up
            Practice by using swipe ups to increase the slider to 100%
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Tells user to go to min slider value
     * @author Antony Loose
     */
    private fun speakGoToMin() {
        val goToMin = """
            Well done!
            You can decrease slider values by swiping down.
            Practice by swiping down until the slider value is 50%
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(goToMin)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Jade Davis
     */
    private fun speakOutro() {
        // TODO: better outro
        val outro = """
            Well done you now know how to adjust a slider by swiping up or down.
            You will now be taken to the next part of this lesson
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(outro)
    }

    private fun finishFragment(){
        this.ttsEngine.onFinishedSpeaking {
            parentFragmentManager.commit {
                replace(this@AdjustSliderModulePart1Fragment.id,
                    AdjustSliderModulePart2Fragment.newInstance()
                )
                addToBackStack("adjustSliderModulePart2")
            }
        }
        this.speakOutro()
    }
}