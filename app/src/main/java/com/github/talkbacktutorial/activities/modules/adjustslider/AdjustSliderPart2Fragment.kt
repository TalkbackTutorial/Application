package com.github.talkbacktutorial.activities.modules.adjustslider

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentAdjustSliderModulePart2Binding

class AdjustSliderPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AdjustSliderPart2Fragment()
    }

    private lateinit var binding: FragmentAdjustSliderModulePart2Binding
    lateinit var ttsEngine: TextToSpeechEngine

    private lateinit var mainView: ConstraintLayout
    private lateinit var menuSlider: SeekBar

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
        val intro = getString(R.string.adjust_slider_part2_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Tells user to go to min slider value
     * @author Antony Loose
     */
    private fun speakGoToMin() {
        val goToMin = getString(R.string.adjust_slider_part2_go_to_min).trimIndent()
        this.ttsEngine.speak(goToMin)
    }

    /**
     * Speaks an outro for the fragment.
     * @author Antony Loose
     */
    private fun speakOutro() {
        val outro = getString(R.string.adjust_slider_part2_outro).trimIndent()
        this.ttsEngine.speak(outro)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Antony Loose
     */
    private fun finishLesson() {
        updateModule()
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.finish()
        }
        speakOutro()
    }

    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}
