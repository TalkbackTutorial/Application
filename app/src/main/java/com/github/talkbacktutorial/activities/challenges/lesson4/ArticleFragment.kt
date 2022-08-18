package com.github.talkbacktutorial.activities.challenges.lesson4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.startstopmedia.CustomVideoView
import com.github.talkbacktutorial.databinding.FragmentArticleBinding
import java.util.*
import kotlin.concurrent.schedule

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var context: Lesson4ChallengeActivity
    private var videoStarted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.context = activity as Lesson4ChallengeActivity
        this.setUpVideo()
    }

    /**
     * Sets up the embedded video for the article.
     * @author Andre Pham
     */
    private fun setUpVideo() {
        val videoView = this.binding.articleVideo
        videoView.setVideoPath(getString(R.string.android_resource) + requireActivity().packageName + getString(R.string.forward_slash) + R.raw.animated_man)
        videoView.setOnPreparedListener { it.isLooping = true }
        val mediaController = MediaController(context)
        videoView.setOnClickListener {
            mediaController.show(0)
        }
        mediaController.setAnchorView(videoView)
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
        videoView.setPlayPauseListener(object : CustomVideoView.PlayPauseListener {
            override fun onPlay() {
                if (videoView.currentPosition == 0 && this@ArticleFragment.videoStarted) {
                    Timer().schedule(1000) {
                        this@ArticleFragment.context.completeChallenge()
                    }
                } else {
                    this@ArticleFragment.videoStarted = true
                }
            }
            override fun onPause() { }
        })
        videoView.visibility = View.VISIBLE // Otherwise Talkback immediately jumps to the video
    }
}