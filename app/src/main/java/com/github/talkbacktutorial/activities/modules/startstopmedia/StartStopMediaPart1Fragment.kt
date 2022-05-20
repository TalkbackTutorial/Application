package com.github.talkbacktutorial.activities.modules.startstopmedia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentStartStopMediaModulePart1Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class StartStopMediaPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StartStopMediaPart1Fragment()
    }

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
        this.ttsEngine = TextToSpeechEngine((activity as StartStopMediaActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.binding.startStopMediaControlConstraintLayout.visibility = View.VISIBLE
        }
        setUpVideo()
        this.speakIntro()
    }

    private fun setUpVideo() {
        // Get videoView by id
        this.videoView = this.binding.customVideoview
        videoView.setVideoPath("android.resource://" + requireActivity().packageName + "/" + R.raw.video_test)
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        mediaController = MediaController(context)
        mediaController?.setAnchorView(videoView)
        mediaController?.setMediaPlayer(videoView)
        mediaController?.requestFocus()

        // Set play pause listener
        setUpPlayPauseListener()
        mediaController?.setEnabled(true)
        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
    }

    private fun setUpPlayPauseListener() {
        videoView.setPlayPauseListener(object : CustomVideoView.PlayPauseListener {
            override fun onPause() {
                if (!firstPlay) {
                    binding.startStopMediaControlConstraintLayout.visibility = View.GONE
                    ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                        binding.startStopMediaControlConstraintLayout.visibility = View.VISIBLE
                    }
                    mediaController?.hide()
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
            }
            override fun onPlay() {
                mediaController?.hide()
                if (firstPlay) {
                    val info = """
                        Great job! You've played the video.
                        Now that you learned how to play the video, we will try pause the video.
                        To pause the video, explore by touch to find the pause button and double tap
                        on the button to pause the video.
                    """.trimIndent()

                    Handler().postDelayed(
                        Runnable {
                            videoView.pause()
                            ttsEngine.speak(info)
                            binding.startStopMediaControlConstraintLayout.visibility = View.GONE
                            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                                binding.startStopMediaControlConstraintLayout.visibility = View.VISIBLE
                                firstPlay = false
                                videoView.start()
                            }
                        },
                        1000
                    )
                }
            }
        })
    }
    private fun speakIntro() {
        val intro = """
            In this tutorial, you will be learning how to start and stop a video. 
            To start, double tap your screen by touch to bring up the media controller. 
            Next swipe right until you find the play button. Double tap to start playing the video.
            If nothing happens when you swipe, double tap again to bring up the media controller.
        """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    private fun insertFinishButton() {
        val constraintLayout = this.binding.startStopMediaControlConstraintLayout
        val primaryButtonBinding: WidePillButtonBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wide_pill_button, constraintLayout,false)
        primaryButtonBinding.text = "Finish Lesson"
        primaryButtonBinding.button.setOnClickListener{ endLesson() }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.horizontalBias = 0.95f
        layoutParams.endToEnd = constraintLayout.id
        layoutParams.startToStart = constraintLayout.id
        layoutParams.topToTop = constraintLayout.id
        layoutParams.topMargin = 10.dpToPixels(requireContext())
        constraintLayout.addView(primaryButtonBinding.button, layoutParams)
    }

    private fun endLesson() {
        if (mediaController != null && mediaController!!.isShowing()) {
            mediaController!!.hide()
        }

        // Lesson's complete go back to Main Activity
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    private fun Int.dpToPixels(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()
}
