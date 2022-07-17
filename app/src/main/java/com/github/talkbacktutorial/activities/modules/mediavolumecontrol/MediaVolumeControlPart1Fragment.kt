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
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.DebugSettings
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentMediaVolumeControlModulePart1Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding
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
                    if (accessibilityManager.isEnabled && accessibilityManager.isTouchExplorationEnabled || DebugSettings.talkbackNotRequired) {
                        if (mediaPlayer == null) {
                            val info = getString(R.string.media_volume_control_part1_play_prompt).trimIndent()
                            speakDuringLesson(info)
                        } else lessonLogic()
                    } else speakDuringLesson(getString(R.string.media_volume_control_part1_talkback_prompt))
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
            val info = getString(R.string.media_volume_control_part1_decrease_volume).trimIndent()
            ttsEngine.speak(info)
            swipeUp = true
        } else if (currentVolume <= 25 && swipeUp) {
            stopMedia()
            val info = getString(R.string.media_volume_control_part1_outro).trimIndent()
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
        val primaryButtonBinding: WidePillButtonBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wide_pill_button, constraintLayout,false)
        primaryButtonBinding.text = ""
        primaryButtonBinding.button.setOnClickListener{ endLesson() }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.horizontalBias = 0.95f
        layoutParams.endToEnd = constraintLayout.id
        layoutParams.startToStart = constraintLayout.id
        layoutParams.topToTop = constraintLayout.id
        layoutParams.topMargin = 10.dpToPixels(requireContext())
        constraintLayout.addView(primaryButtonBinding.button, layoutParams)
    }

    private fun speakIntro() {
        val intro = getString(R.string.media_volume_control_part1_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    private fun endLesson() {
        updateModule()

        /* Lesson's complete go back to Main Activity */
        val intent = Intent((activity as MediaVolumeControlActivity), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

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
