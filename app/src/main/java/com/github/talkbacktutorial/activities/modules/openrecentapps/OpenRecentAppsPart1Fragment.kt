package com.github.talkbacktutorial.activities.modules.openrecentapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentOpenRecentAppsPart1Binding

class OpenRecentAppsPart1Fragment : Fragment() {

    private lateinit var binding: FragmentOpenRecentAppsPart1Binding
    private lateinit var display: TextView
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_open_recent_apps_part1,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.display = view.findViewById<TextView>(R.id.o_recent_app_p1_tv)
        this.displayIntro()
        // Simple listener detecting a change in window focus
        view.viewTreeObserver?.addOnWindowFocusChangeListener { _ ->
            count++
            // If the count is equal to 3, the app must have lost focus and re-gained focus
            if (count == 3) {
                finishLesson()
            }
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jai Clapp
     */
    private fun displayIntro() {
        val intro = getString(R.string.open_recent_apps_part1_intro).trimIndent()
        this.display.text = intro

    }

    /**
     * Completes the module
     * @author Jai Clapp
     */
    private fun finishLesson() {
        val outro = getString(R.string.open_recent_apps_part1_outro).trimIndent()
        this.display.text = outro

        this.display.setOnClickListener {
            val ft : FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(this@OpenRecentAppsPart1Fragment.id, OpenRecentAppsPart2Fragment())
            ft.addToBackStack(getString(R.string.open_recent_apps_part1_backstack))
            ft.commit()
        }
    }
}
