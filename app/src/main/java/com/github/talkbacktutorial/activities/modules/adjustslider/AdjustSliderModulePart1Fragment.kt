package com.github.talkbacktutorial.activities.modules.adjustslider

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.modules.scrolling.ScrollingModuleActivity
import com.github.talkbacktutorial.activities.modules.scrolling.ScrollingModulePart1Fragment
import com.github.talkbacktutorial.databinding.FragmentAdjustSliderModulePart1Binding
import com.github.talkbacktutorial.databinding.FragmentScrollingModulePart1Binding

class AdjustSliderModulePart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AdjustSliderModulePart1Fragment()
    }

    private lateinit var binding: FragmentAdjustSliderModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    lateinit var menuSlider: SeekBar
//    lateinit var menuSliderText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adjust_slider_module_part1, container, false)
        this.menuSlider = this.binding.menuSlider
//        this.menuSliderText = this.binding.seekBarValueTV
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as AdjustSliderModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.menuSlider.visibility = View.VISIBLE
                setSliderHandler()
            }
        this.speakIntro()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jade Davis
     */
    private fun speakIntro() {
        val intro = """
            Testing testing testing.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    fun setSliderHandler() {
        this.menuSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                binding.seekBarValueTV.setText(i.toString())
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })
    }
}