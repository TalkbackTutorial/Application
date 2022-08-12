package com.github.talkbacktutorial.activities.challenges.lesson3

import android.content.Context
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
import org.w3c.dom.Text
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cookieHeader: View
        val cookieDescription: View
        val cookieLink: View


        // strings and labels
        val promptTemplate = getString(R.string.jump_navigation_easy_prompt)

        cookieHeader = binding.challenge.cookieHeader
        cookieDescription = binding.challenge.cookieDescription
        cookieLink = binding.challenge.cookieLink


        val targetTemplate = getString(R.string.jump_navigation_target_link_template)

        // set up text
        cookieHeader.text = getString(R.string.cookie_header)
        cookieDescription.text = getString(R.string.cookie_description)
        val cookieLinkText = HtmlCompat.fromHtml(getString(R.string.cookie_link), HtmlCompat.FROM_HTML_MODE_COMPACT)
        cookieLink.text = cookieLinkText

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
    }


    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}