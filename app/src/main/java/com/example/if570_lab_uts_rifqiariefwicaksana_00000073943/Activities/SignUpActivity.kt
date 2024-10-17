package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val MAX_USERS = 2  // Limit to 2 users
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)

        // Access SharedPreferences
        val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        var userCount = sharedPrefs.getInt("userCount", 0)

        // Check if the maximum number of users has been reached
        if (userCount >= MAX_USERS) {
            Toast.makeText(this, "Maximum users reached. Cannot create more accounts.", Toast.LENGTH_LONG).show()
            navigateToLogin()  // Redirect to LoginActivity
            finish()
            return
        }

        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Validate inputs
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Store new user credentials dynamically
            sharedPrefs.edit().apply {
                putString("user_${userCount}_email", email)
                putString("user_${userCount}_password", password)
                putInt("userCount", ++userCount)  // Increment user count
                apply()
            }

            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
