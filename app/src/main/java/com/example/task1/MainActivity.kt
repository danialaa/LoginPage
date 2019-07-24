package com.example.task1

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.view.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    //variables to check if text in editTexts is validated
    var usernameValidated : Boolean = false
    var passwordValidated : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(te: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //check if rules are met in username
                if (s.toString().length >= 4 && s.toString().length <= 40) {
                    //set check mark
                    usernameText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.validate, 0)
                    usernameValidated = true
                } else {
                    //remove check mark
                    usernameText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    usernameValidated = false
                }
            }
        })
        passwordText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(te: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //regex to check if rules are met in username
                val special = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()}{~`_%!-]")
                val caps = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]")
                val smol = Pattern.compile("[abcdefghijklmnopqrstuvwxyz]")
                val nums = Pattern.compile("[0123456789]")

                //check if rules are met in password
                if (special.matcher(s).find() && caps.matcher(s).find() && smol.matcher(s).find() && nums.matcher(s).find()) {
                    //set check mark
                    passwordText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.validate, 0)
                    passwordValidated = true
                } else {
                    //remove check mark
                    passwordText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    passwordValidated = false
                }
            }
        })
        loginButton.setOnClickListener {
            //check if editTexts are empty, incorrect or correct and send dialog details to function
            if (usernameText.text.isEmpty() || passwordText.text.isEmpty()) {
                createDialog("Invalid Login", "Username and Password Fields are Required", "sad")
            } else if (!usernameValidated || !passwordValidated || !usernameText.text.toString().equals("Admin") || !passwordText.text.toString().equals("P@ssw0rd")) {
                createDialog("Invalid Login", "Username or Password Not Correct", "sad")
            } else {
                createDialog("Successful", "You are Logged In!", "happy")
            }
        }
    }

    fun createDialog (title: String, content: String, emoji: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val mAlertDialog = builder.show()

        //window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        //set dialog details
        dialogView.titleText.text = title
        dialogView.contentText.text = content

        if (emoji.equals("sad")) {
            dialogView.emoji.setImageResource(R.drawable.sad)
        } else {
            dialogView.emoji.setImageResource(R.drawable.happy)
        }

        dialogView.okayButton.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}
