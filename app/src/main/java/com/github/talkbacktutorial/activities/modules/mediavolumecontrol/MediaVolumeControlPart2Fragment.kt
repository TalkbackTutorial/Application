package com.github.talkbacktutorial.activities.modules.mediavolumecontrol

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentMediaVolumeControlModulePart1Binding


class MediaVolumeControlPart2Fragment: Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MediaVolumeControlPart2Fragment()
    }

    private lateinit var binding: FragmentMediaVolumeControlModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    private var mediaPlayer:MediaPlayer? = null
    private lateinit var audioManager:AudioManager
    private var totalSongDuration:Int = 0
    private var seekbarVolume:SeekBar? = null
    private var currentVolume: Int = 50
    private var swipeUp:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media_volume_control_module_part1, container, false)
        return this.binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as MediaVolumeControlModuleActivity)).onFinishedSpeaking(triggerOnce = true){
        }

        // media setup
        audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager

        this.seekbarVolume = this.binding.volumeControlSeekBar
        mediaControls()

        // tts introduction
        this.speakIntro()
    }

    private fun mediaControls(){
        this.binding.playButton.setOnClickListener{
            // when users click on the fab button
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(context, R.raw.media_sound)
            }

            mediaPlayer?.start()
            mediaPlayer?.isLooping = true
            mediaPlayer?.setVolume(0.5f, 0.5f)
            totalSongDuration = mediaPlayer!!.duration
            seekbarVolume?.progress = 50

        }

        this.binding.stopButton.setOnClickListener{
            stopMedia()
        }

        //TODO test this with Talkback
        this.seekbarVolume?.setOnClickListener{
            val info = "Media Volume Slider. Swipe up or down to increase or decrease the volume".trimIndent()
            this.ttsEngine.speak(info)
        }
        this.seekbarVolume?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2) {
                    currentVolume = p1
                    var volumeNum = p1/100.0f
                    mediaPlayer?.setVolume(volumeNum, volumeNum)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

                if(currentVolume >= 75){
                    val info = """
                        current Volume is set above 75.
                        Now that you learned how to increase the volume, we will try decreasing the volume below 25.
                        To decrease the volume, swipe down.
                    """.trimIndent()

                    ttsEngine.speak(info)
                    swipeUp = true
                }

                if(currentVolume <= 25 && swipeUp){
                    val info = """
                        current volume is set below 25.
                        Congratulations on completing this lesson.
                        In this lesson you have successfully learn how to control the media volume of your phone.
                        To exit this lesson, select the finish button on the screen.
                    """.trimIndent()

                    ttsEngine.speak(info)
                    insertFinishButton()
                }

            }
        })
    }

    private fun stopMedia(){
        if(mediaPlayer !== null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

     private fun insertFinishButton(){
         val constraintLayout = this.binding.mediaVolumeControlConstraintLayout
         val finishButton = Button(requireContext())
         finishButton.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
         finishButton.text = "Finish"
         finishButton.setBackgroundResource(R.color.green_A400)
         finishButton.setOnClickListener(View.OnClickListener {
             endLesson()
         })
         constraintLayout.addView(finishButton);
     }

    private fun speakIntro(){
        //TODO retype the introduction
        val intro = """
            In this tutorial, you will be learning how to control the media volume sliders, increasing or decreasing your phone's media volume. 
            To start, explore your screen by touch and you find the play button. 
            Play the music.
            Then, increasing the volume above 75 by swiping up.
            """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    private fun endLesson(){
        /* Lesson's complete go back to Main Activity */
        val intent = Intent((activity as MediaVolumeControlModuleActivity), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        stopMedia()
        startActivity(intent)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
