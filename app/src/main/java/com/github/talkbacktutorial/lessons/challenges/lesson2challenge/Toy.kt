package com.github.talkbacktutorial.lessons.challenges.lesson2challenge

import java.util.*

/**
 * A toy, such as one you'd see in the store.
 * @author Andre Pham
 * @param name The name of the toy
 * @param price The price of the toy
 * @param description A description of the toy
 */
class Toy(
    val name: String,
    val price: Double,
    val description: String
) {
    val starRating: Int = (1..5).random()
    val id: UUID = UUID.randomUUID()
}
