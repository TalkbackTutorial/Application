package com.github.talkbacktutorial.activities.modules.openbraillekeyboardsettings

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.github.talkbacktutorial.R


class OpenBrailleKeyboardSettingsActivity : AppCompatActivity() {
    private lateinit var textBox: EditText
    private lateinit var textDisplay: TextView
    private lateinit var contBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_braille_keyboard_settings)
        supportActionBar?.hide()
        this.textBox = findViewById(R.id.brailleSettingsEditText)
        this.textDisplay = findViewById(R.id.brailleSettingsTextView)
        this.contBtn = findViewById(R.id.brailleSettingsContinueBtn)
        this.contBtn.visibility = View.GONE
        this.contBtn.text = getString(R.string.continueStr)

        this.textDisplay.text = getString(R.string.brailleSettingsIntro)

        textBox.addTextChangedListener {
            if (isProbablyArabic(it.toString())) {
                this.textDisplay.text = getString(R.string.brailleSettingsOutro)
                this.textBox.visibility = View.GONE
                this.contBtn.visibility = View.VISIBLE
            }
        }
        contBtn.setOnClickListener{ finish() }
    }

    /**
     * Checks if the given string contains any arabic characters
     * taken from https://stackoverflow.com/questions/15107313/how-to-determine-a-string-is-english-or-arabic
     * translated into Kotlin by Android studio
     */
    private fun isProbablyArabic(s: String): Boolean {
        var i = 0
        while (i < s.length) {
            val c = s.codePointAt(i)
            if (c in 0x0600..0x06E0) return true
            i += Character.charCount(c)
        }
        return false
    }

}