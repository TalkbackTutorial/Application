package com.github.talkbacktutorial.activities.challenges.lesson2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentInspectToyBinding
import com.github.talkbacktutorial.lessons.challenges.lesson2challenge.Toy
import com.github.talkbacktutorial.lessons.challenges.lesson2challenge.ToyContainer
import java.util.*
import kotlin.concurrent.schedule

private const val TOY_PARAM = "toyParam"

class InspectToyFragment : Fragment() {

    private lateinit var toy: Toy
    private lateinit var binding: FragmentInspectToyBinding
    private lateinit var context: Lesson2ChallengeActivity

    companion object {
        @JvmStatic
        fun newInstance(toy: Toy) = InspectToyFragment().apply {
            arguments = Bundle().apply {
                putString(TOY_PARAM, toy.id.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.toy = ToyContainer.getToy(UUID.fromString(it.getString(TOY_PARAM)!!)) ?: return
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspect_toy, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.context = activity as Lesson2ChallengeActivity
        this.binding.name = this.toy.name
        this.binding.price = this.toy.price
        this.binding.description = this.toy.description
        this.binding.rating.contentDescription = getString(R.string.rating_content_description, this.toy.starRating)
        for (star in 1..this.toy.starRating) {
            val starImage = ImageView(this.context)
            starImage.setBackgroundResource(R.drawable.baseline_star_yellow_800_24dp)
            this.binding.ratingLayout.addView(starImage)
        }
        this.binding.buyButton.button.setOnClickListener {
            Toast.makeText(this.context, getString(R.string.purchased_feedback, this.toy.name), Toast.LENGTH_SHORT).show()
        }
        this.binding.addToWatchlistButton.button.setOnClickListener {
            this.context.watchlist.addToy(this.toy)
            Toast.makeText(this.context, getString(R.string.added_to_watchlist_feedback, this.toy.name), Toast.LENGTH_SHORT).show()
            Timer().schedule(2000) {
                this@InspectToyFragment.context.checkChallengeComplete()
            }
        }
    }
}