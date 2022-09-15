package com.github.talkbacktutorial.activities.challenges.lesson6

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.challenges.lesson6.adapter.ChatAdapter
import com.github.talkbacktutorial.activities.challenges.lesson6.model.ChatModel
import com.github.talkbacktutorial.databinding.FragmentLesson6ChallengePart1Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding


class Lesson6ChallengePart1Fragment : Fragment(){
    companion object {
        @JvmStatic
        fun newInstance() = Lesson6ChallengePart1Fragment()
    }

    private lateinit var binding: FragmentLesson6ChallengePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var context: Lesson6ChallengeActivity

    private var recyclerView: RecyclerView? = null
    private var messageBox: EditText? = null
    private var sendButton: Button? = null
    private val chatModels: ArrayList<ChatModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson6_challenge_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.context = activity as Lesson6ChallengeActivity
        //initialise view
        recyclerView = this.binding.chatList
        messageBox = this.binding.etChatBox
        sendButton = this.binding.btnChatSend

        this.ttsEngine = TextToSpeechEngine((activity as Lesson6ChallengeActivity))
        binding.lesson6ChallengeConstraintLayout.visibility = View.GONE
        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            binding.lesson6ChallengeConstraintLayout.visibility = View.VISIBLE
        }
        speakIntro()

        sendButton!!.setOnClickListener {
            var i = 1
            i++
            val chatModel = ChatModel()
            chatModel.setId(i)
            chatModel.setMe(true)
            chatModel.setMessage(messageBox!!.text.toString().trim { it <= ' ' })
            chatModels.add(chatModel)
            val chatAdapter = ChatAdapter(chatModels, this.context)
            recyclerView!!.setHasFixedSize(true)
            recyclerView!!.layoutManager = LinearLayoutManager(this.context)
            recyclerView!!.adapter = chatAdapter
            validateText(messageBox!!.text.toString())
            Log.i("testing",messageBox!!.text.toString())
            messageBox!!.setText("")
        }
    }

    /**
     * Function that checks if the text sent by the user is correct
     * @author Team4
     */
    private fun validateText(textBoxString: String) {
        if (textBoxString.lowercase().contains(getString(R.string.lesson6_challenge_text_message))){
            this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                this.endLesson()
            }
            Handler().postDelayed({
                this.ttsEngine.speak(getString(R.string.challenge_outro))
            },3000)
        }
    }

    /**
     * Function that exits the module.
     * @author Sandy Du
     */
    private fun endLesson() {
        // Lesson's complete go back to Main Activity
        val intent = Intent(this.context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Natalie Law, Sandy Du & Nabeeb Yusuf
     */
    private fun speakIntro() {
        val intro = getString(R.string.lesson6_challenge_fragment1_intro)
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Function that converts DPI/Pixel values to integer
     * @author Sandy Du
     */
    private fun Int.dpToPixels(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    /**
     * Clean up TTS when fragment is destroyed
     *
     * @author Matthew Crossman
     */
    override fun onDestroyView() {
        ttsEngine.shutDown()
        super.onDestroy()
    }

}