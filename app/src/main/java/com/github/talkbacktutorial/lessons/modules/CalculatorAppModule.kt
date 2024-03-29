package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.calculatorapp.CalculatorAppActivity

/**
 * This module involves performing a set of tasks within a custom fork of Simple Calculator.
 *
 * @author Team 3
 * @see <a href="https://github.com/TalkbackTutorial/calculator-fork">Calculator fork repo (app being used here)</a>
 */
class CalculatorAppModule: Module() {

    override val title = "Calculator App"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, CalculatorAppActivity::class.java))
    }
}