package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textViewSignUp = findViewById<TextView>(R.id.textViewSignUp)

        val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            val userCount = sharedPrefs.getInt("userCount", 0)
            var isAuthenticated = false

            // Loop through all registered users and check credentials
            for (i in 0 until userCount) {
                val savedEmail = sharedPrefs.getString("user_${i}_email", "")
                val savedPassword = sharedPrefs.getString("user_${i}_password", "")

                if (email == savedEmail && password == savedPassword) {
                    isAuthenticated = true
                    break
                }
            }

            if (isAuthenticated) {
                sharedPrefs.edit().putBoolean("isLoggedIn", true).apply()
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                navigateToHome()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
