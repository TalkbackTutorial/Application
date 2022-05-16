package com.github.talkbacktutorial.lessons.challenges.lesson2challenge

import android.content.res.Resources
import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * Contains all toys to be retrieved and used within the application.
 * @author Andre Pham
 */
object ToyContainer {

    private val toys = ArrayList<Toy>(
        listOf(
            Toy(App.resources.getString(R.string.tennis_ball), 5.0, App.resources.getString(R.string.tennis_ball_description)),
            Toy(App.resources.getString(R.string.tennis_racquet), 180.0, App.resources.getString(R.string.tennis_racquet_description)),
            Toy(App.resources.getString(R.string.basket_ball), 20.0, App.resources.getString(R.string.basket_ball_description)),
            Toy(App.resources.getString(R.string.teddy_bear), 40.0, App.resources.getString(R.string.teddy_bear_description)),
            Toy(App.resources.getString(R.string.princess_doll), 55.0, App.resources.getString(R.string.princess_doll_description)),
            Toy(App.resources.getString(R.string.plastic_castle), 100.0, App.resources.getString(R.string.plastic_castle_description)),
            Toy(App.resources.getString(R.string.rc_car), 200.0, App.resources.getString(R.string.rc_car_description)),
            Toy(App.resources.getString(R.string.robot_figurine), 160.0, App.resources.getString(R.string.robot_figurine_description)),
            Toy(App.resources.getString(R.string.unicorn_plush), 80.0, App.resources.getString(R.string.unicorn_plush_description)),
            Toy(App.resources.getString(R.string.plastic_kitchen_set), 150.0, App.resources.getString(R.string.plastic_kitchen_set_description)),
            Toy(App.resources.getString(R.string.playing_cards), 10.0, App.resources.getString(R.string.playing_cards_description)),
            Toy(App.resources.getString(R.string.train_set), 90.0, App.resources.getString(R.string.train_set_description)),
            Toy(App.resources.getString(R.string.toy_guitar), 170.0, App.resources.getString(R.string.toy_guitar_description)),
            Toy(App.resources.getString(R.string.egg), 99.0, App.resources.getString(R.string.egg_description))
        )
    )

    fun getOrderedToys(minPrice: Double, maxPrice: Double): ArrayList<Toy> {
        val filteredByPriceToys = this.toys.filter { it.price in minPrice..maxPrice }
        return ArrayList(filteredByPriceToys.sortedBy { it.price })
    }

    fun getToy(id: UUID): Toy {
        return this.toys.first { toy ->
            toy.id == id
        }
    }

    fun getToy(name: String): Toy {
        return this.toys.first { toy ->
            toy.name == name
        }
    }
}
