package com.github.talkbacktutorial.activities.challenges.lesson5

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentLesson5ChallengePart2Binding


class Lesson5ChallengePart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson5ChallengePart2Fragment()
    }
    private lateinit var binding: FragmentLesson5ChallengePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private val CHANNEL_ID = "TBT1"
    private val notificationId = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson5_challenge_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as Lesson5ChallengeActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                createNotificationChannel()
                sendNotification()
            }
        this.speakIntro()
    }

    /**
     * Create the notification channel
     * @author Jason Wu
     */
    private fun createNotificationChannel(){
        val name = "Talkback tutorial"
        val descriptionText = "Notification description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Send the notification to user
     * @author Jason Wu
     */
    private fun sendNotification(){
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Return to main activity by double tap the notification
        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.lesson5_challenge_notification_title))
            .setContentText(getString(R.string.lesson5_challenge_outro))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(requireContext())){
            notify(notificationId, builder.build())
        }
        this.ttsEngine.stopSpeaking()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jason Wu
     */
    private fun speakIntro() {
        val intro = getString(R.string.lesson5_challenge_fragment2_intro)
        this.ttsEngine.speakOnInitialisation(intro)
    }
}