package com.github.talkbacktutorial.activities.challenges.lesson3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
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

        val checkbox = binding.checkboxIngredient1
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                this@CookieRecipeFragment.context.completeChallenge()
            }
        }
    }
}
