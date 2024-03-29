package com.github.talkbacktutorial.gestures

import com.github.talkbacktutorial.gestures.data.FlingMotionData
import com.github.talkbacktutorial.gestures.data.GestureData
import com.github.talkbacktutorial.gestures.data.ScrollMotionData
import com.github.talkbacktutorial.gestures.data.TapData

/**
 * Used to identify a Talkback gesture.
 * Uses fling, tap, gesture and scroll data retrieved via delegates.
 * @author Andre Pham
 */
class GestureIdentifier {

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    val flingMotionData = FlingMotionData()
    val tapData = TapData()
    val gestureData = GestureData()
    val scrollMotionData = ScrollMotionData()

    /**
     * A callback to trigger whenever a gesture is fully concluded, in order to identify
     * the performed Talkback gesture.
     * @return The enum representing the matching gesture
     * @author Andre Pham
     */
    fun onGestureConclusion(): TalkbackGesture {
        val gesture = this.identifyGesture()
        if (gesture.isFlingGesture()) {
            this.tapData.reset()
        }
        return gesture
    }

    /**
     * Identify, based on the current data, which Talkback gesture matches the gesture performed.
     * @return The enum representing the matching gesture
     * @author Andre Pham
     */
    private fun identifyGesture(): TalkbackGesture {
        this.tapData.postReset()

        if (this.scrollMotionData.verifiedNotTap() && this.scrollMotionData.pointersAreAligned()) {
            if (this.scrollMotionData.isHorizontalScroll()) {
                if (this.scrollMotionData.distanceX > 0) {
                    when (this.scrollMotionData.pointerCount) {
                        2 -> return TalkbackGesture.RIGHT_2
                        3 -> return TalkbackGesture.RIGHT_3
                    }
                } else {
                    when (this.scrollMotionData.pointerCount) {
                        2 -> return TalkbackGesture.LEFT_2
                        3 -> return TalkbackGesture.LEFT_3
                    }
                }
            } else {
                if (this.scrollMotionData.distanceY > 0) {
                    when (this.scrollMotionData.pointerCount) {
                        2 -> return TalkbackGesture.DOWN_2
                        3 -> return TalkbackGesture.DOWN_3
                    }
                } else {
                    when (this.scrollMotionData.pointerCount) {
                        2 -> return TalkbackGesture.UP_2
                        3 -> return TalkbackGesture.UP_3
                    }
                }
            }
        }

        when (this.tapData.tapCount) {
            1 -> {
                when (this.tapData.pointerCount) {
                    1 -> if (flingMotionData.yVelocity(true) + flingMotionData.xVelocity(true) == 0F) return TalkbackGesture.TAP
                    2 -> return TalkbackGesture.TAP_2
                    3 -> return TalkbackGesture.TAP_3
                }
            }
            2 -> {
                when (this.tapData.pointerCount) {
                    1 -> return TalkbackGesture.DOUBLE_TAP
                    2 -> return TalkbackGesture.DOUBLE_TAP_2
                }
            }
            3 -> {
                when (this.tapData.pointerCount) {
                    2 -> return TalkbackGesture.TRIPLE_TAP_2
                }
            }
        }

        if (!this.flingMotionData.dataIsValid() || this.scrollMotionData.pointerCount > 1 || this.tapData.pointerCount > 1) {
            return TalkbackGesture.NO_MATCH
        }

        val orthogonalPredictions = this.gestureData.getTopPredictions(3, 5)
        var boundingBoxMatchesOrthogonalGesture = false
        if (this.flingMotionData.xDistance() > this.flingMotionData.yDistance() && this.flingMotionData.yDistance() > this.flingMotionData.xDistance()/2) {
            boundingBoxMatchesOrthogonalGesture = true
        }
        else if (this.flingMotionData.yDistance() > this.flingMotionData.xDistance() && this.flingMotionData.xDistance() > this.flingMotionData.yDistance()/2) {
            boundingBoxMatchesOrthogonalGesture = true
        }
        if (boundingBoxMatchesOrthogonalGesture) {
            val gestureEndsHigher = this.flingMotionData.flingEndsHigher()!!
            val gestureEndsFurther = this.flingMotionData.flingEndsFurther()!!
            for (prediction in orthogonalPredictions) {
                when (prediction.name) {
                    "Left Up" -> {
                        if (gestureEndsHigher && !gestureEndsFurther) {
                            return TalkbackGesture.LEFT_UP
                        }
                    }
                    "Left Down" -> {
                        if (!gestureEndsHigher && !gestureEndsFurther) {
                            return TalkbackGesture.LEFT_DOWN
                        }
                    }
                    "Right Up" -> {
                        if (gestureEndsHigher && gestureEndsFurther) {
                            return TalkbackGesture.RIGHT_UP
                        }
                    }
                    "Right Down" -> {
                        if (!gestureEndsHigher && gestureEndsFurther) {
                            return TalkbackGesture.RIGHT_DOWN
                        }
                    }
                    "Down Left" -> {
                        if (!gestureEndsHigher && !gestureEndsFurther) {
                            return TalkbackGesture.DOWN_LEFT
                        }
                    }
                    "Down Right" -> {
                        if (!gestureEndsHigher && gestureEndsFurther) {
                            return TalkbackGesture.DOWN_RIGHT
                        }
                    }
                    "Up Left" -> {
                        if (gestureEndsHigher && !gestureEndsFurther) {
                            return TalkbackGesture.UP_LEFT
                        }
                    }
                    "Up Right" -> {
                        if (gestureEndsHigher && gestureEndsFurther) {
                            return TalkbackGesture.UP_RIGHT
                        }
                    }
                }
            }
        }

        val gestureXDistance = this.gestureData.xDistance()
        val gestureYDistance = this.gestureData.yDistance()
        val gestureXCentre = this.gestureData.xCentre()
        val gestureYCentre = this.gestureData.yCentre()
        val startingPoint = this.flingMotionData.downPoint()!!
        val finishingPoint = this.flingMotionData.upPoint()!!
        if (gestureYDistance < gestureXDistance*0.25) {
            if (finishingPoint.x > gestureXCentre && startingPoint.x > gestureXCentre) {
                return TalkbackGesture.LEFT_RIGHT
            } else if (finishingPoint.x < gestureXCentre && startingPoint.x < gestureXCentre) {
                return TalkbackGesture.RIGHT_LEFT
            }
        } else if (gestureXDistance < gestureYDistance*0.25) {
            if (finishingPoint.y > gestureYCentre && startingPoint.y > gestureYCentre) {
                return TalkbackGesture.UP_DOWN
            } else if (finishingPoint.y < gestureYCentre && startingPoint.y < gestureYCentre) {
                return TalkbackGesture.DOWN_UP
            }
        }

        if (this.flingMotionData.xDistance() > this.flingMotionData.yDistance()) {
            if (this.flingMotionData.xDistance() > SWIPE_THRESHOLD && this.flingMotionData.xVelocity(true) > SWIPE_VELOCITY_THRESHOLD) {
                return if (this.flingMotionData.xOffset() > 0) TalkbackGesture.RIGHT else TalkbackGesture.LEFT
            }
        } else {
            if (this.flingMotionData.yDistance() > SWIPE_THRESHOLD && this.flingMotionData.yVelocity(true) > SWIPE_VELOCITY_THRESHOLD) {
                return if (this.flingMotionData.yOffset() > 0) TalkbackGesture.DOWN else TalkbackGesture.UP
            }
        }

        return TalkbackGesture.NO_MATCH
    }
}