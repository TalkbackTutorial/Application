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


        // strings and labels
        val promptTemplate = getString(R.string.jump_navigation_easy_prompt)

        title = binding.challenge.titleHeader
        cookieHeader = binding.challenge.cookieHeader
        cookieDescription = binding.challenge.cookieDescription
        cookieLink = binding.challenge.cookieLink
        brownieHeader = binding.challenge.brownieHeader
        brownieDescription = binding.challenge.brownieDescription
        brownieLink = binding.challenge.brownieLink



        val targetTemplate = getString(R.string.jump_navigation_target_link_template)

        // set up text
        title.text = getString(R.string.recipe_book_title)
        cookieHeader.text = getString(R.string.cookie_header)
        cookieDescription.text = getString(R.string.cookie_description)
        val cookieLinkText = HtmlCompat.fromHtml(getString(R.string.cookie_link), HtmlCompat.FROM_HTML_MODE_COMPACT)
        cookieLink.text = cookieLinkText
        brownieHeader.text = getString(R.string.brownie_header)
        brownieDescription.text = getString(R.string.brownie_description)
        brownieLink.text = getString(R.string.brownie_link)


//  ************* IMPORTANT!!! - NEED TO CALL UPDATEMODULE() ***********
//        completeChallengeControl.text = getString(R.string.complete_lesson3_challenge)
//        completeChallengeControl.setOnClickListener {
//                updateModule()
//                activity?.finish()
//        }

        super.onViewCreated(view, savedInstanceState)

        // set headings
        ViewCompat.setAccessibilityHeading(cookieHeader, true)

        // set up links
        //cookie = getView(R.string.cookie_link)
        //ViewCompat.set

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