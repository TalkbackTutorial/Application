package com.github.talkbacktutorial.lessons.challenges.lesson2challenge

/**
 * A watchlist of toys.
 * @author Andre Pham
 */
class ToyWatchlist {

    private val watchlist = ArrayList<Toy>()

    fun addToy(toy: Toy) {
        if (!this.containsToy(toy)) {
            this.watchlist.add(toy)
        }
    }

    fun containsToy(toy: Toy): Boolean {
        return this.watchlist.any { it.id == toy.id }
    }
}
