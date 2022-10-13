package com.github.talkbacktutorial.activities.modules.startstopmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentStartStopMediaModulePart1Binding


class StartStopMediaPart1Fragment: Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = StartStopMediaPart1Fragment()
    }

    private lateinit var binding: FragmentStartStopMediaModulePart1Binding
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_stop_media_module_part1, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.textView = view.findViewById(R.id.start_stop_media_intro_textview)
        this.displayIntro()
        this.textView.setOnClickListener {
            val ft : FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(this@StartStopMediaPart1Fragment.id, StartStopMediaPart2Fragment())
            //ft.addToBackStack(getString(R.string.open_recent_apps_part1_backstack))
            ft.commit()
        }
    }

    private fun displayIntro() {
        val intro = getString(R.string.start_stop_media_part1_intro).trimIndent()
        this.textView.text = intro
    }
}