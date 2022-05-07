package com.github.talkbacktutorial.activities.modules.startstopmedia

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.modules.mediavolumecontrol.MediaVolumeControlModuleActivity
import com.github.talkbacktutorial.databinding.FragmentStartStopMediaModulePart1Binding
import com.github.talkbacktutorial.activities.modules.startstopmedia.CustomVideoView

class StartStopMediaPart1Fragment: Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StartStopMediaPart1Fragment()
    }
    private var TAG = "VideoPlayer"
    private lateinit var binding: FragmentStartStopMediaModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var videoView: CustomVideoView
    private var mediaController: MediaController? = null
    private var firstPlay: Boolean = true
    private var firstPause: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_stop_media_module_part1, container, false)
        this.binding.startStopMediaControlConstraintLayout.visibility = View.INVISIBLE
        return this.binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as StartStopMediaModuleActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.binding.startStopMediaControlConstraintLayout.visibility = View.VISIBLE
        }

        // Get videoView by id
        this.videoView = this.binding.customVideoview
        videoView.setVideoPath("android.resource://" + requireActivity().packageName + "/" + R.raw.video_test)
        /*
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
        */
        mediaController?.setAnchorView(videoView);
        mediaController?.setMediaPlayer(videoView);
        mediaController?.requestFocus();
        mediaController = MediaController(context)
        /*videoView.setOnPreparedListener(object: MediaPlayer.OnPreparedListener{
            override fun onPrepared(mp: MediaPlayer) {
                mediaController?.show(0)
            }
        })*/

        // Set play pause listener
        videoView.setPlayPauseListener(object: CustomVideoView.PlayPauseListener{
            override fun onPause() {
                Log.i(TAG, "PAUSE = " + videoView.isPlaying)
                val info = """
                        Great job! You've paused the video.
                        Congratulations on completing this lesson.
                        In this lesson you have successfully learn how to play and pause a video.
                        To exit this lesson, select the finish button on the screen.
                    """.trimIndent()

                ttsEngine.speak(info)
                firstPause = false
                insertFinishButton()
            }

            override fun onPlay() {
                Log.i(TAG, "PLAY = " + videoView.isPlaying)

                if (firstPlay){
                    val info = """
                        Great job! You've played the video.
                        Now that you learned how to play the video, we will try pause the video.
                        To pause the video, explore by touch to find the pause button and double tap
                        on the button to pause the video.
                    """.trimIndent()

                    ttsEngine.speak(info)
                    firstPlay = false
                }


            }
        })
        mediaController?.setEnabled(true)
        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        this.speakIntro()
    }

    private fun speakIntro(){
        val intro = """
            In this tutorial, you will be learning how to start and stop a video. 
            To start, double tap your screen by touch to bring up the media controller. 
            Next swipe right until you find the play button. Double tap to start playing the video.
            If nothing happens when you swipe, double tap again to bring up the media controller.
            """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    private fun insertFinishButton(){
        val constraintLayout = this.binding.startStopMediaControlConstraintLayout
        val finishButton = Button(requireContext())
        finishButton.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        finishButton.text = "Finish"
        finishButton.setBackgroundResource(R.color.green_A400)
        finishButton.setOnClickListener(View.OnClickListener {
            endLesson()
        })
        constraintLayout.addView(finishButton)
    }

    private fun endLesson(){
        /* Lesson's complete go back to Main Activity */
        val intent = Intent((activity as StartStopMediaModuleActivity), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}