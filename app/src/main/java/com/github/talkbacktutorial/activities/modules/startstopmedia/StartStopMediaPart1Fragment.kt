package com.github.talkbacktutorial.activities.modules.startstopmedia

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
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
        videoView.setVideoPath(getString(R.string.android_resource) + requireActivity().packageName + getString(R.string.forward_slash) + R.raw.video_test)
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
                    val info = getString(R.string.start_stop_media_part1_outro).trimIndent()

                    ttsEngine.speak(info)
                    firstPause = false
                    insertFinishButton()
                }
            }
            override fun onPlay() {
                mediaController?.hide()
                if (firstPlay) {
                    val info = getString(R.string.start_stop_media_part1_play).trimIndent()

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
        val intro = getString(R.string.start_stop_media_part1_intro).trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    private fun insertFinishButton() {
        val constraintLayout = this.binding.startStopMediaControlConstraintLayout
        val primaryButtonBinding: WidePillButtonBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wide_pill_button, constraintLayout,false)
        primaryButtonBinding.text = getString(R.string.finish_lesson)
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
        updateModule()
        if (mediaController != null && mediaController!!.isShowing()) {
            mediaController!!.hide()
        }
        // Lesson's complete go back to Main Activity
        activity?.finish()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    private fun Int.dpToPixels(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

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
