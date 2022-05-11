package com.github.talkbacktutorial.activities.modules.goback

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentGobackModulePart2Binding

class GoBackModulePart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = GoBackModulePart2Fragment()
    }

    private lateinit var binding: FragmentGobackModulePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_goback_module_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as GoBackModuleActivity))
        GoBackModulePart1Fragment.returning = true
        this.speakIntro()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Emmanuel Chu
     */
    private fun speakIntro() {
        val intro = """
            To navigate back to the previous page, swipe down then left
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        Log.v("onDestroyView", "func called")
        this.ttsEngine.shutDown()
        super.onDestroyView()

    }




}