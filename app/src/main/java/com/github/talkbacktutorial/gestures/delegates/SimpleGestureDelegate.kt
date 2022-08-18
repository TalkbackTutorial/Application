package com.github.talkbacktutorial.gestures.delegates

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import com.github.talkbacktutorial.gestures.data.FlingMotionData
import com.github.talkbacktutorial.gestures.data.ScrollMotionData
import com.github.talkbacktutorial.gestures.data.TapData

/**
 * A class to delegate simple gesture callbacks.
 * Used to retrieve fling, tap and scroll gesture data.
 * @author Andre Pham
 */
class SimpleGestureDelegate(
    context: Context,
    private val flingMotionData: FlingMotionData,
    private val tapData: TapData,
    private val scrollMotionData: ScrollMotionData
): GestureDetector.SimpleOnGestureListener() {

    private val detector = GestureDetector(context, this)

    /**
     * A callback to be triggered on a touch event.
     * @param event The motion event triggering the touch event.
     * @author Andre Pham
     */
    fun onTouchEventCallback(event: MotionEvent) {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            this.scrollMotionData.reset()
            this.flingMotionData.reset()
            this.tapData.tapCount += 1
        } else if (event.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
            this.tapData.pointerCount = event.pointerCount
        }
        this.detector.onTouchEvent(event)
    }

    override fun onFling(
        downMotionEvent: MotionEvent?,
        upMotionEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        this.flingMotionData.setData(downMotionEvent, upMotionEvent, velocityX, velocityY)
        return super.onFling(downMotionEvent, upMotionEvent, velocityX, velocityY)
    }

    override fun onScroll(
        downMotionEvent: MotionEvent?,
        upMotionEvent: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        this.scrollMotionData.setData(downMotionEvent, upMotionEvent, upMotionEvent?.pointerCount ?: 1)
        return super.onScroll(downMotionEvent, upMotionEvent, distanceX, distanceY)
    }

}