package com.github.talkbacktutorial.activities.challenges.lesson6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.challenges.lesson6.adapter.ChatAdapter
import com.github.talkbacktutorial.activities.challenges.lesson6.model.ChatModel
import com.github.talkbacktutorial.databinding.FragmentLesson6ChallengePart1Binding



class Lesson6ChallengePart1Fragment : Fragment(){
    companion object {
        @JvmStatic
        fun newInstance() = Lesson6ChallengePart1Fragment()
    }

    //TODO: Binding Issue
    private lateinit var binding: FragmentLesson6ChallengePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    private var recyclerView: RecyclerView? = null
    private var messageBox: EditText? = null
    private var sendButton: Button? = null
    val chatModels: ArrayList<ChatModel> = ArrayList()

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
        //initialise view
        recyclerView = this.binding.chatList
        messageBox = this.binding.etChatBox
        sendButton = this.binding.btnChatSend

        this.ttsEngine = TextToSpeechEngine((activity as Lesson6ChallengeActivity))
            .onFinishedSpeaking(triggerOnce = true) {
            }
        this.speakIntro()

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
            messageBox!!.setText("")
        }
    }


    /**
     * Speaks an intro for the fragment.
     * @author Natalie Law, Sandy Du & Nabeeb Yusuf
     */
    private fun speakIntro() {
        val intro = getString(R.string.lesson6_challenge_fragment1_intro)
        this.ttsEngine.speakOnInitialisation(intro)
    }





}