package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.submittext.SubmitTextActivity

/**
 * A module that teaches the user how to submit text on the virtual braille keyboard.
 * @author Jai Clapp
 */
class SubmitTextModule : Module() {

    override val title: String = "Submit Text"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, SubmitTextActivity::class.java))
    }
}