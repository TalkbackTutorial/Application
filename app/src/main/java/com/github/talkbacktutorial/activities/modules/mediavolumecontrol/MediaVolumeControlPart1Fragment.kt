package com.github.talkbacktutorial.activities.modules.mediavolumecontrol

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.Button
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentMediaVolumeControlModulePart1Binding
import kotlin.math.roundToInt

class MediaVolumeControlPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MediaVolumeControlPart1Fragment()
    }

    private lateinit var binding: FragmentMediaVolumeControlModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var audioManager: AudioManager
    private var totalSongDuration: Int = 0
    private var seekbarVolume: SeekBar? = null
    private var currentVolume: Int = 0
    private var maxStreamVolume: Int = 150
    private var swipeUp: Boolean = false
    private lateinit var accessibilityManager: AccessibilityManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media_volume_control_module_part1, container, false)
        this.binding.mediaVolumeControlConstraintLayout.visibility = View.INVISIBLE
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as MediaVolumeControlActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.binding.mediaVolumeControlConstraintLayout.visibility = View.VISIBLE
        }

        // media and accessibility manager setup
        audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        accessibilityManager = requireActivity().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        this.seekbarVolume = this.binding.volumeControlSeekBar
        seekbarVolume?.progress = 50
        mediaControls()

        // tts introduction
        this.speakIntro()
    }

    private fun mediaControls() {
        this.binding.playButton.setOnClickListener {
            // when users click on the fab button
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, R.raw.media_sound)
            }

            mediaPlayer?.start()
            mediaPlayer?.isLooping = true
            val defaultStreamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            // TODO add function
            totalSongDuration = mediaPlayer!!.duration
            maxStreamVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val vol: Float = defaultStreamVolume.toFloat() / maxStreamVolume
            mediaPlayer?.setVolume(vol, vol)
            currentVolume = (vol * 100).roundToInt()
            seekbarVolume?.progress = currentVolume
        }

        this.binding.stopButton.setOnClickListener {
            stopMedia()
        }

        this.seekbarVolume?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    currentVolume = p1
                    val vol: Float = p1.toFloat() / maxStreamVolume
                    mediaPlayer?.setVolume(vol, vol)
                    var volumeNum = p1 / 100.0f
                    mediaPlayer?.setVolume(volumeNum, volumeNum)
                    // If talkback is turned on
                    if (accessibilityManager.isEnabled && accessibilityManager.isTouchExplorationEnabled) {
                        if (mediaPlayer == null) {
                            val info = """The song is not played. Please press the play button to play the music.""".trimIndent()
                            speakDuringLesson(info)
                        } else lessonLogic()
                    } else speakDuringLesson("Talkback is currently not turned on. Please turn Talkback on to continue this lesson")
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    private fun lessonLogic() {
        if (currentVolume >= 75) {
            if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
            binding.mediaVolumeControlConstraintLayout.visibility = View.GONE
            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                binding.mediaVolumeControlConstraintLayout.visibility = View.VISIBLE
                if (mediaPlayer?.isPlaying == false) mediaPlayer?.start()
            }
            val info = """
                        current Volume is set above 75.
                        Now that you learned how to increase the volume, we will try decreasing the volume below 25.
                        To decrease the volume, swipe down.
            """.trimIndent()
            ttsEngine.speak(info)
            swipeUp = true
        } else if (currentVolume <= 25 && swipeUp) {
            stopMedia()
            val info = """
                        current volume is set below 25.
                        Congratulations on completing this lesson.
                        In this lesson you have successfully learn how to control the media volume of your phone.
                        To exit this lesson, select the finish button on the top right corner of the screen.
            """.trimIndent()
            speakDuringLesson(info)
            insertFinishButton()
        }
    }

    private fun stopMedia() {
        if (mediaPlayer !== null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun insertFinishButton() {
        val constraintLayout = this.binding.mediaVolumeControlConstraintLayout
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.horizontalBias = 0.95f
        layoutParams.endToEnd = constraintLayout.id
        layoutParams.startToStart = constraintLayout.id
        layoutParams.topToTop = constraintLayout.id
        layoutParams.topMargin = 10.dpToPixels(requireContext())
        val finishButton = Button(requireContext())
        val text = "Finish"
        finishButton.contentDescription = text
        finishButton.text = text
        finishButton.layoutParams = layoutParams
        finishButton.setBackgroundResource(R.color.primary40)
        finishButton.setOnClickListener(
            View.OnClickListener {
                endLesson()
            }
        )

        constraintLayout.addView(finishButton)
    }

    private fun speakIntro() {
        val intro = """
            In this tutorial, you will be learning how to control the media volume sliders, increasing or decreasing your phone's media volume. 
            To start, explore your screen by touch and find the play button. 
            Once found, double tap to play the music.
            Then, increase the volume of the media above 75 by swiping up.
        """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    private fun endLesson() {
        stopMedia()
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        stopMedia()
        super.onDestroyView()
    }

    private fun Int.dpToPixels(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    private fun speakDuringLesson(info: String) {
        binding.mediaVolumeControlConstraintLayout.visibility = View.GONE
        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            binding.mediaVolumeControlConstraintLayout.visibility = View.VISIBLE
        }
        ttsEngine.speak(info)
    }
}
