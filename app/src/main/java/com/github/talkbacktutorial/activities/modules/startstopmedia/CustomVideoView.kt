package com.github.talkbacktutorial.activities.modules.startstopmedia

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

/**
 * Custom VideoView class to be used with lesson 6. This class is inherited from the VideoView class
 * and the functions pause, start has been override while an interface has been added to implement
 * two functions when a video is played/paused.
 * @author Sandy Du & Nabeeb Yusuf
 */

class CustomVideoView : VideoView {

    private var mListener: PlayPauseListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    /**
     * Assigns the listener to observe.
     */
    fun setPlayPauseListener(listener: PlayPauseListener?) {
        mListener = listener
    }

    /**
     * Call the onPause function on the listener when the video is paused.
     */
    override fun pause() {
        super.pause()
        if (mListener != null) {
            mListener!!.onPause()
        }
    }

    /**
     * Call the onPlay function on the listener when the video is started.
     */
    override fun start() {
        super.start()
        if (mListener != null) {
            mListener!!.onPlay()
        }
    }

    /**
     * Interface that the listener needs to implement.
     */
    interface PlayPauseListener {
        fun onPlay()
        fun onPause()
    }
}
