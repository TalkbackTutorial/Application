package com.github.talkbacktutorial.activities.modules.startstopmedia

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentStartStopMediaModulePart2Binding

class StartStopMediaPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StartStopMediaPart2Fragment()
    }

    private lateinit var binding: FragmentStartStopMediaModulePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var videoView: CustomVideoView
    private var mediaController: MediaController? = null
    private var firstPlay: Boolean = true
    private var firstPause: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_stop_media_module_part2, container, false)
        //this.binding.startStopMediaControlConstraintLayout.visibility = View.INVISIBLE
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as StartStopMediaActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.binding.startStopMediaControlConstraintLayout.visibility = View.VISIBLE
        }
        setUpVideo()
    }

    /**
     * Function that sets up the video to be used in the CustomVideoView.
     * @author Sandy Du
     */
    private fun setUpVideo() {
        // Get videoView by id
        this.videoView = this.binding.customVideoview
        videoView.setVideoPath(getString(R.string.android_resource) + requireActivity().packageName + getString(R.string.forward_slash) + R.raw.video_test)
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        mediaController = MediaController(context)
        mediaController?.setAnchorView(videoView)
        mediaController?.setMediaPlayer(videoView)
        mediaController?.requestFocus()

        // Set play pause listener
        setUpPlayPauseListener()
        mediaController?.isEnabled = true
        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
    }

    /**
     * Function that sets up the class as a listener for CustomVideoView. It implements and overrides
     * the onPause and onPlay functions from CustomVideoView to use the TTSEngine to give audio
     * feedback.
     * @author Sandy Du & Nabeeb Yusuf
     */
    private fun setUpPlayPauseListener() {
        videoView.setPlayPauseListener(object : CustomVideoView.PlayPauseListener {
            override fun onPause() {
                if (!firstPlay) {
                    binding.startStopMediaControlConstraintLayout.visibility = View.GONE
                    ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                        binding.startStopMediaControlConstraintLayout.visibility = View.VISIBLE
                    }
                    mediaController?.hide()
                    val info = getString(R.string.start_stop_media_part1_outro).trimIndent()

                    ttsEngine.speak(info)
                    firstPause = false
                    ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                        endLesson()
                    }
                }
            }
            override fun onPlay() {
                mediaController?.hide()
                if (firstPlay) {
                    val info = getString(R.string.start_stop_media_part1_play).trimIndent()

                    Handler().postDelayed(
                        {
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


    /**
     * Function that is called when the finish button is pressed.
     * @author Sandy Du
     */
    private fun endLesson() {
        updateModule()
        if (mediaController != null && mediaController!!.isShowing) {
            mediaController!!.hide()
        }
        // Lesson's complete go back to Main Activity
        activity?.finish()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
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
