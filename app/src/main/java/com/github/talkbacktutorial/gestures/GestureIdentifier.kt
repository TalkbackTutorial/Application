package com.github.talkbacktutorial.gestures

import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.util.Log
import android.widget.Switch
import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.gestures.data.FlingMotionData
import com.github.talkbacktutorial.gestures.data.GestureData
import com.github.talkbacktutorial.gestures.data.MoveMotionData
import com.github.talkbacktutorial.gestures.data.TapData

class GestureIdentifier {

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    val flingMotionData = FlingMotionData()
    val tapData = TapData()
    val gestureData = GestureData()
    val moveMotionData = MoveMotionData()

    fun onGestureConclusion(): TalkbackGesture {
//        Log.i("andre", this.gestureData.toString())
//        Log.i("andre", this.flingMotionData.toString())
//        Log.i("andre", this.moveMotionData.toString())

        if (this.tapData.tapCount == 2) {
            return TalkbackGesture.DOUBLE_TAP
        }

        if (!this.flingMotionData.dataIsValid()) {
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