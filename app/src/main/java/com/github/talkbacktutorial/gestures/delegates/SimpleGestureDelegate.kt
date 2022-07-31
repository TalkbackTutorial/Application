package com.github.talkbacktutorial.gestures.delegates

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.github.talkbacktutorial.gestures.data.FlingMotionData
import com.github.talkbacktutorial.gestures.data.ScrollMotionData
import com.github.talkbacktutorial.gestures.data.TapData
import kotlin.math.abs

class SimpleGestureDelegate(
    context: Context,
    private val flingMotionData: FlingMotionData,
    private val tapData: TapData,
    private val scrollMotionData: ScrollMotionData
): GestureDetector.SimpleOnGestureListener() {

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    private val detector = GestureDetector(context, this)

    fun onTouchEventCallback(event: MotionEvent) {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            this.scrollMotionData.reset()
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

        val xDelta: Float = upMotionEvent?.x?.minus(downMotionEvent!!.x) ?: 0.0F
        val yDelta: Float = upMotionEvent?.y?.minus(downMotionEvent!!.y) ?: 0.0F
        val horizontalThreshold = abs(xDelta) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
        val verticalThreshold = abs(yDelta) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD
        return horizontalThreshold || verticalThreshold
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