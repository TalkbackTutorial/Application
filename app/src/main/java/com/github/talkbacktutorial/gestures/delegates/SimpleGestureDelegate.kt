package com.github.talkbacktutorial.gestures.delegates

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import com.github.talkbacktutorial.gestures.data.FlingMotionData
import com.github.talkbacktutorial.gestures.data.MoveMotionData
import com.github.talkbacktutorial.gestures.data.TapData
import kotlin.math.abs

class SimpleGestureDelegate(
    context: Context,
    private val flingMotionData: FlingMotionData,
    private val tapData: TapData,
    private val moveMotionData: MoveMotionData
): GestureDetector.SimpleOnGestureListener() {

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    private val detector = GestureDetector(context, this)

    fun onTouchEventCallback(event: MotionEvent) {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            this.moveMotionData.clear()
            this.tapData.reset()
        } else if (event.actionMasked == MotionEvent.ACTION_MOVE) {
            this.moveMotionData.pullDataFrom(event)
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

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        this.tapData.tapCount = 2
        return super.onDoubleTap(e)
    }

}