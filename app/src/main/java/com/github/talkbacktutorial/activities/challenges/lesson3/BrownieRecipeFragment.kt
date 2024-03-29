package com.github.talkbacktutorial.activities.challenges.lesson3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentBrownieRecipeBinding

class BrownieRecipeFragment: Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = BrownieRecipeFragment()
    }

    private lateinit var binding: FragmentBrownieRecipeBinding
    private lateinit var context: Lesson3ChallengeActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_brownie_recipe, container, false)
        this.context = activity as Lesson3ChallengeActivity
        return binding.root
    }

    @SuppressLint("PrivateResource", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val brownieHeader: View
        val ingredientsHeader: View
        val methodHeader: View

        brownieHeader = binding.brownieRecipeTitle
        ingredientsHeader = binding.ingredientsHeading
        methodHeader = binding.methodHeading

        // set headers
        ViewCompat.setAccessibilityHeading(brownieHeader, true)
        ViewCompat.setAccessibilityHeading(ingredientsHeader, true)
        ViewCompat.setAccessibilityHeading(methodHeader, true)

    }
}