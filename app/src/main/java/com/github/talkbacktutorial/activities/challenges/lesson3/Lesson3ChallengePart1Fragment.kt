package com.github.talkbacktutorial.activities.challenges.lesson3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentLesson3ChallengePart1Binding
import java.util.*

class Lesson3ChallengePart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson3ChallengePart1Fragment()
    }

    private lateinit var binding: FragmentLesson3ChallengePart1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson3_challenge_part1, container, false)
        return binding.root
    }

    @SuppressLint("PrivateResource")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title: View
        val cookieHeader: View
        val cookieDescription: View
        val cookieLink: View
        val brownieHeader: View
        val brownieDescription: View
        val brownieLink: View

        super.onViewCreated(view, savedInstanceState)

        // strings and labels
        title = binding.challenge.titleHeader
        cookieHeader = binding.challenge.cookieHeader
        cookieDescription = binding.challenge.cookieDescription
        cookieLink = binding.challenge.cookieLink
        brownieHeader = binding.challenge.brownieHeader
        brownieDescription = binding.challenge.brownieDescription
        brownieLink = binding.challenge.brownieLink

        // set up text
        title.text = getString(R.string.recipe_book_title)
        cookieHeader.text = getString(R.string.cookie_header)
        cookieDescription.text = getString(R.string.cookie_description)
        brownieHeader.text = getString(R.string.brownie_header)
        brownieDescription.text = getString(R.string.brownie_description)



        // set headers
        ViewCompat.setAccessibilityHeading(title, true)
        ViewCompat.setAccessibilityHeading(brownieHeader, true)
        ViewCompat.setAccessibilityHeading(cookieHeader, true)

        cookieLink.setOnClickListener{
            parentFragmentManager.beginTransaction().setCustomAnimations(
                com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in,
                com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
                .replace(this@Lesson3ChallengePart1Fragment.id, CookieRecipeFragment.newInstance())
                .addToBackStack("cookieRecipe")
                .commit()
        }

        brownieLink.setOnClickListener{
            parentFragmentManager.beginTransaction().setCustomAnimations(
                com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in,
                com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
                .replace(this@Lesson3ChallengePart1Fragment.id, BrownieRecipeFragment.newInstance())
                .addToBackStack("brownieRecipe")
                .commit()
        }
    }
}