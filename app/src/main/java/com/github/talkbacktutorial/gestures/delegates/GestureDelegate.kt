package com.github.talkbacktutorial.gestures.delegates

import android.gesture.Gesture
import android.gesture.GestureOverlayView
import android.view.MotionEvent
import com.github.talkbacktutorial.gestures.data.GestureData

/**
 * A class to delegate gesture callbacks.
 * Used to retrieve gesture data on a gesture's completion.
 * @author Andre Pham
 */
class GestureDelegate(
    private val gestureData: GestureData
): GestureOverlayView.OnGestureListener {

    override fun onGestureStarted(p0: GestureOverlayView?, p1: MotionEvent?) {
        // Do nothing
    }

    override fun onGesture(p0: GestureOverlayView?, p1: MotionEvent?) {
        // Do nothing
    }

    override fun onGestureEnded(overlay: GestureOverlayView?, event: MotionEvent?) {
        this.gestureData.setData(overlay?.gesture ?: Gesture())
    }

    override fun onGestureCancelled(p0: GestureOverlayView?, p1: MotionEvent?) {
        // Do nothing
    }
}