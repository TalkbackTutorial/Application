package com.github.talkbacktutorial.activities.modules.dlnbraillekeyboard

/**
 * This has been commented out by Andre Pham due to stopping the application from compiling.
 * It is the responsibility of Mohak Malhotra and Jai Clapp to restore this.
 * https://github.com/TalkbackTutorial/Application/issues/39
 */

//import android.os.Bundle
//import android.view.*
//import android.view.inputmethod.EditorInfo
//import android.widget.TextView
//import android.widget.TextView.OnEditorActionListener
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.commit
//import com.github.talkbacktutorial.R
//import com.github.talkbacktutorial.TextToSpeechEngine
//import com.github.talkbacktutorial.databinding.*
//import java.util.*
//import kotlin.concurrent.schedule
//import android.text.Editable
//import android.text.TextWatcher
//
//
//class DNLBrailleKeyboardPart1Fragment : Fragment(){
//
//    private lateinit var binding: FragmentAddSpacesNlBrailleKeyboardPart1Binding
//    private lateinit var ttsEngine: TextToSpeechEngine
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        this.binding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_dnl_braille_keyboard_part1,
//            container,
//            false
//        )
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        this.ttsEngine = TextToSpeechEngine((activity as DNLBrailleKeyboardActivity))
//        binding.editText.visibility = View.INVISIBLE
//        this.speakIntro()
//        this.setupText()
//        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
//            binding.editText.visibility = View.VISIBLE
//        }
//    }
//
//    /**
//     * this will initialise text submit listener.
//     * @author Mohak Malhotra
//     */
//    private fun setupText() {
//
//        binding.editText.setText("L",TextView.BufferType.EDITABLE)
//
//        binding.editText.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {
//                val text = binding.editText.text.toString()
//                // Checking for correct user space input.
//                if (text.lowercase() == "") {
//                    finishLesson()
//                }
//                else {
//                    val error = getString(R.string.dnl_braille_part1_error).trimIndent()
//                    ttsEngine.speak(error)
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//            }
//        })
//    }
//
//    /**
//     * Speaks an intro for the fragment.
//     * @author Mohak Malhotra
//     */
//    private fun speakIntro() {
//        val intro = getString(R.string.dnl_braille_part1_intro).trimIndent()
//        ttsEngine.speakOnInitialisation(intro)
//    }
//
//    override fun onDestroyView() {
//        this.ttsEngine.shutDown()
//        super.onDestroyView()
//    }
//
//    /**
//     * Completes the module
//     * @author Mohak Malhotra
//     */
//    private fun finishLesson() {
//        Timer().schedule(6000) {
//            val outro = getString(R.string.dnl_braille_part1_outro).trimIndent()
//            println("\n\nFINISHED\n\n")
//            ttsEngine.speak(outro)
//            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
//                parentFragmentManager.commit {
//                    replace(this@DNLBrailleKeyboardPart1Fragment.id, DNLBrailleKeyboardPart2Fragment())
//                    addToBackStack("dlbraillekeyboardpart1")
//                }
//            }
//        }
//
//    }
//}
