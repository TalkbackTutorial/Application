package com.github.talkbacktutorial.activities.modules.addspacesnlbraillekeyboard

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


class AddSpacesNLBrailleKeyboardActivity : AppCompatActivity() {
    private lateinit var textBox: EditText
    private lateinit var textDisplay: TextView
    private lateinit var contBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_spaces_nl_braille_keyboard)
        supportActionBar?.hide()
        this.textBox = findViewById(R.id.brailleSettingsEditText)
        this.textDisplay = findViewById(R.id.brailleSettingsTextView)
        this.contBtn = findViewById(R.id.brailleSettingsContinueBtn)
        this.contBtn.visibility = View.GONE
        this.contBtn.text = getString(R.string.continueStr)

        this.textDisplay.text = getString(R.string.add_spaces_nl_braille_intro)

        textBox.addTextChangedListener {
            if (isProbablyArabic(it.toString())) {
                this.textDisplay.text = getString(R.string.brailleSettingsOutro)
                this.textBox.visibility = View.GONE
                this.contBtn.visibility = View.VISIBLE
            }
        }
        contBtn.setOnClickListener{ finish() }
    }
    

}