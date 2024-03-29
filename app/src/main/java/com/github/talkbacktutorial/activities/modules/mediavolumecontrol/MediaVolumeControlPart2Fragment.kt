package com.github.talkbacktutorial.activities.modules.mediavolumecontrol

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.DebugSettings
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentMediaVolumeControlModulePart2Binding

class MediaVolumeControlPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MediaVolumeControlPart2Fragment()
    }

    private lateinit var binding: FragmentMediaVolumeControlModulePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var audioManager: AudioManager
    private var totalSongDuration: Int = 0
    private var seekbarVolume: SeekBar? = null
    private var currentVolume: Int = 0
    private var swipeUp: Boolean = false
    private var initialVolume: Int = 50
    private lateinit var accessibilityManager: AccessibilityManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media_volume_control_module_part2, container, false)
        //this.binding.mediaVolumeControlConstraintLayout.visibility = View.INVISIBLE
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

    /**
     * Set up the media control components: play button, stop button and volume slider
     * @author Natalie Law
     */
    private fun mediaControls() {
        this.binding.playButton.setOnClickListener {
            val info = getString(R.string.media_volume_control_part1_increase_volume).trimIndent()
            ttsEngine.speak(info)

            // when users click on the fab button
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, R.raw.media_sound)
            }

            // changing the volume of the phone's audio
            mediaPlayer?.start()
            mediaPlayer?.isLooping = true
            totalSongDuration = mediaPlayer!!.duration
            val vol: Float = initialVolume.toFloat() / 100.0F
            mediaPlayer?.setVolume(vol, vol)
            seekbarVolume?.progress = initialVolume
        }

        this.binding.stopButton.setOnClickListener {
            stopMedia()
        }

        this.seekbarVolume?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    currentVolume = p1
                    val vol: Float = p1.toFloat() / 100.0F
                    mediaPlayer?.setVolume(vol, vol)
                    // If talkback is turned on
                    if (accessibilityManager.isEnabled && accessibilityManager.isTouchExplorationEnabled || DebugSettings.talkbackNotRequired) {
                        if (mediaPlayer == null) {
                            val info = getString(R.string.media_volume_control_part1_play_prompt).trimIndent()
                            speakDuringLesson(info)
                        } else lessonLogic()
                    } else speakDuringLesson(getString(R.string.media_volume_control_part1_talkback_prompt))
                    Log.i("Testing", "p1 $p1")
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    /**
     * Determines the logic of the lesson. It first prompts the user to increase the volume to 75 by swiping up.
     * Then it checks whether the volume is above 75; or below 25 (and has previously
     * completed the swipe up gesture). Based on the conditions, the method determines the next steps for the lesson.
     * @author Natalie Law
     */
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
            binding.mediaVolumeControlConstraintLayout.visibility = View.GONE
            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                endLesson()
            }
            val info = getString(R.string.media_volume_control_part1_outro).trimIndent()
            ttsEngine.speak(info)
        }
    }

    /**
     * Stop the media player from playing the music
     * @author Natalie Law
     */
    private fun stopMedia() {
        if (mediaPlayer !== null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    /**
     * Introduction of the lesson module using TTS
     * @author Natalie Law
     */
    private fun speakIntro() {
        val intro = getString(R.string.media_volume_control_part1_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * End the lesson and bring user back to the Main Activity
     * @author Natalie Law
     */
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

    /**
     * This method is used to ensure that the TTS will not be interrupted when speaking during the lesson. This is
     * done by hiding the view when TTS is speaking and when finished, set the view back the visible.
     * @author Natalie Law
     */
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
