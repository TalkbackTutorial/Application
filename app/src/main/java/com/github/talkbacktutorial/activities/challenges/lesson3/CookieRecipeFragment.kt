package com.github.talkbacktutorial.activities.challenges.lesson3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cookie_recipe, container, false)
        return binding.root
    }

}
