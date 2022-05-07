package com.github.talkbacktutorial.activities.modules.startstopmedia

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentStartStopMediaModulePart1Binding

class StartStopMediaPart1Fragment: Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StartStopMediaPart1Fragment()
    }

    private lateinit var binding: FragmentStartStopMediaModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var videoView: VideoView
    private var mediaController: MediaController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_stop_media_module_part1, container, false)
        return this.binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as StartStopMediaModuleActivity)).onFinishedSpeaking(triggerOnce = true){
        }

        // Get videoView by id
        this.videoView = this.binding.videoView
        videoView.setVideoPath("android.resource://" + requireActivity().packageName + "/" + R.raw.video_test)
        mediaController = object: MediaController(context){
            //for not hiding
            override fun hide() {
                mediaController?.show(0)
            }

            //for 'back' key action
            override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                    val a = context as Activity
                    a.finish()
                }
                return true
            }
        }
        mediaController?.setAnchorView(videoView);
        mediaController?.setMediaPlayer(videoView);
        mediaController?.requestFocus();
        mediaController = MediaController(context)
        videoView.setOnPreparedListener(object: MediaPlayer.OnPreparedListener{
            override fun onPrepared(mp: MediaPlayer) {
                //videoView.start()
                mediaController?.show(0)
            }
        })
        mediaController?.setEnabled(true)
        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        //this.videoView.start()
        this.speakIntro()
    }

    private fun speakIntro(){
        val intro = """
            Welcome. In this tutorial, you will be learning how to play and pause a video using the media controller. 
            To start, explore your screen by touch and you find the play button.
            """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }



}