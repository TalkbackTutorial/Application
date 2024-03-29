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
import com.github.talkbacktutorial.databinding.FragmentCookieRecipeBinding

class CookieRecipeFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = CookieRecipeFragment()
    }

    private lateinit var binding: FragmentCookieRecipeBinding
    private lateinit var context: Lesson3ChallengeActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cookie_recipe, container, false)
        this.context = activity as Lesson3ChallengeActivity
        return binding.root
    }

    @SuppressLint("PrivateResource", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val cookieHeader: View
        val ingredientsHeader: View
        val methodHeader: View

        cookieHeader = binding.cookieRecipeTitle
        ingredientsHeader = binding.ingredientsHeading
        methodHeader = binding.methodHeading

        // set headers
        ViewCompat.setAccessibilityHeading(cookieHeader, true)
        ViewCompat.setAccessibilityHeading(ingredientsHeader, true)
        ViewCompat.setAccessibilityHeading(methodHeader, true)

        val checkbox = binding.checkboxCookieIngredient6
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                this@CookieRecipeFragment.context.completeChallenge()
            }
        }
    }
}
