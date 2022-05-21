package com.github.talkbacktutorial.activities.modules.hidebraillekeyboard

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import java.util.*
import kotlin.concurrent.schedule

class HideBrailleKeyboardActivity : AppCompatActivity() {
    private lateinit var textBox: EditText
    private lateinit var textDisplay: TextView
    private lateinit var contBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hide_braille_keyboard)
        supportActionBar?.hide()
        this.textBox = findViewById(R.id.brailleHideEditText)
        this.textDisplay = findViewById(R.id.brailleHideTextView)
        this.contBtn = findViewById(R.id.brailleHideContinueBtn)
        this.contBtn.visibility = View.GONE
        this.contBtn.text = getString(R.string.continueStr)
        this.textDisplay.text = getString(R.string.hideBrailleIntro)

        textBox.addTextChangedListener {
            if (it.toString() == "a") {
                this.textDisplay.text = getString(R.string.hideBrailleOutro)
                this.textBox.visibility = View.GONE
                this.contBtn.visibility = View.VISIBLE
            }
        }

        textBox.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            // the user fail if they close the keyboard using the submit text gesture
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                textBox.setText("")
                this.textDisplay.text = getString(R.string.hideBrailleFail)
                contBtn.visibility = View.GONE
                textBox.visibility = View.VISIBLE
                return@OnEditorActionListener true
            }
            false
        })

        contBtn.setOnClickListener{finish()}
    }
}