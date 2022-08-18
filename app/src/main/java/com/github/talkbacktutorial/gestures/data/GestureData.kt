package com.github.talkbacktutorial.gestures.data

import android.gesture.Gesture
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.Prediction
import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import kotlin.math.abs

/**
 * Stores and manipulates data relating to a single gesture.
 * Specifically relates to the Gesture class to use in conjunction with GestureLibrary.
 * @author Andre Pham
 */
class GestureData {

    private var gesture: Gesture = Gesture()
    private val gestureLibrary: GestureLibrary = GestureLibraries.fromRawResource(App.context, R.raw.orthogonal_gestures)

    init {
        this.gestureLibrary.load()
    }

    /**
     * Assign the gesture.
     * @param gesture The gesture to store
     * @author Andre Pham
     */
    fun setData(gesture: Gesture) {
        this.gesture = gesture
    }

    /**
     * Calculate the top predictions for what orthogonal gesture could have been performed.
     * @param count The number of top predictions to retrieve, e.g. top 3 predictions
     * @param thresholdScore The required score each top prediction must have to be included
     * @return The top orthogonal gesture predictions, where each meet the provided thresholdScore
     * @author Andre Pham
     */
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

    /**
     * The horizontal distance of the gesture.
     * @return The (always positive) horizontal distance
     * @author Andre Pham
     */
    fun xDistance(): Float {
        return abs(this.gesture.boundingBox.width())
    }

    /**
     * The vertical distance of the gesture.
     * @return The (always positive) vertical distance
     * @author Andre Pham
     */
    fun yDistance(): Float {
        return abs(this.gesture.boundingBox.height())
    }

    /**
     * The x coordinate of the centre point of the bounding box of the gesture.
     * @return The centre x coordinate of the gesture
     * @author Andre Pham
     */
    fun xCentre(): Float {
        return this.gesture.boundingBox.centerX()
    }

    /**
     * The y coordinate of the centre point of the bounding box of the gesture.
     * @return The centre y coordinate of the gesture
     * @author Andre Pham
     */
    fun yCentre(): Float {
        return this.gesture.boundingBox.centerY()
    }

    override fun toString(): String {
        return "Gesture Bounding Box: " + this.gesture.boundingBox.toShortString()
    }

}