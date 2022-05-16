package com.github.talkbacktutorial.activities.challenges.lesson2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.lesson1.Lesson1Part2Fragment
import com.github.talkbacktutorial.databinding.FragmentToyShopBinding
import com.github.talkbacktutorial.databinding.ToyCardBinding
import com.github.talkbacktutorial.lessons.challenges.lesson2challenge.Toy
import com.github.talkbacktutorial.lessons.challenges.lesson2challenge.ToyContainer
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ToyShopFragment : Fragment() {

    private lateinit var binding: FragmentToyShopBinding
    private lateinit var context: Lesson2ChallengeActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_toy_shop, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.context = activity as Lesson2ChallengeActivity
        this.showToysInRange()
        binding.priceSlider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("AUD")
            format.format(value.toDouble())
        }
        binding.priceSlider.addOnChangeListener { rangeSlider, value, fromUser ->
            this.showToysInRange()
        }
    }

    /**
     * Clears all shown toys, then reloads them based on the price slider's values.
     * @author Andre Pham
     */
    private fun showToysInRange() {
        val minPrice = binding.priceSlider.values[0].toDouble()
        val maxPrice = binding.priceSlider.values[1].toDouble()
        this.binding.toyLayout.removeAllViews()
        this.setupToyCards(ToyContainer.getOrderedToys(minPrice, maxPrice))
    }

    /**
     * Displays an arraylist of toys as cards. Clicking opens a new fragment to inspect the toy.
     * @author Andre Pham
     */
    private fun setupToyCards(toys: ArrayList<Toy>) {
        for (toy in toys) {
            val toyCardBinding: ToyCardBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.toy_card, binding.toyLayout, false
            )
            toyCardBinding.setName(toy.name)
            toyCardBinding.setPrice(toy.price)
            toyCardBinding.rating.contentDescription = getString(R.string.rating_content_description, toy.starRating)
            for (star in 1..toy.starRating) {
                val starImage = ImageView(this.context)
                starImage.setBackgroundResource(R.drawable.baseline_star_yellow_800_24dp)
                toyCardBinding.ratingLayout.addView(starImage)
            }
            this.binding.toyLayout.addView(toyCardBinding.card)
            toyCardBinding.card.setOnClickListener {
                parentFragmentManager.beginTransaction().setCustomAnimations(
                    com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in,
                    com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
                    .replace(this@ToyShopFragment.id, InspectToyFragment.newInstance(toy))
                    .addToBackStack("inspectToy")
                    .commit()
            }
        }
    }
}