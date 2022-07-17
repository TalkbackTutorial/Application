package com.github.talkbacktutorial.activities.modules.goback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentGobackModulePart2Binding

class GoBackPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = GoBackPart2Fragment()
    }

    private lateinit var binding: FragmentGobackModulePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_goback_module_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as GoBackActivity))
        GoBackPart1Fragment.returning = true
        this.speakIntro()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Emmanuel Chu
     */
    private fun speakIntro() {
        val intro = getString(R.string.go_back_part2_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

}
