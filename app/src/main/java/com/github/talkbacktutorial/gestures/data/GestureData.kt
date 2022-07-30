package com.github.talkbacktutorial.gestures.data

import android.gesture.Gesture
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.Prediction
import android.util.Log
import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import kotlin.math.abs

class GestureData {

    private var gesture: Gesture = Gesture()
    private val gestureLibrary: GestureLibrary = GestureLibraries.fromRawResource(App.context, R.raw.orthogonal_gestures)

    init {
        this.gestureLibrary.load()
    }

    fun setData(gesture: Gesture) {
        this.gesture = gesture
    }

    fun getTopPredictions(count: Int, thresholdScore: Int): ArrayList<Prediction> {
        val result = ArrayList<Prediction>()
        val predictions = this.gestureLibrary.recognize(this.gesture)
        if (predictions.isEmpty()) { return result }
        predictions.sortBy { it.score }
        predictions.reverse()
        for (index in 0..count) {
            if (index > predictions.size - 1) { break }
            if (predictions[index].score < thresholdScore) { break }
            result.add(predictions[index])
        }
        return result
    }

    fun xDistance(): Float {
        return abs(this.gesture.boundingBox.width())
    }

    fun yDistance(): Float {
        return abs(this.gesture.boundingBox.height())
    }

    fun xCentre(): Float {
        return this.gesture.boundingBox.centerX()
    }

    fun yCentre(): Float {
        return this.gesture.boundingBox.centerY()
    }

    override fun toString(): String {
        return "Gesture Bounding Box: " + this.gesture.boundingBox.toShortString()
    }

}