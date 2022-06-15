package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.calculatorapp.CalculatorAppActivity

class CalculatorAppModule: Module() {

    override val title = "Calculator App"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, CalculatorAppActivity::class.java))
    }
}