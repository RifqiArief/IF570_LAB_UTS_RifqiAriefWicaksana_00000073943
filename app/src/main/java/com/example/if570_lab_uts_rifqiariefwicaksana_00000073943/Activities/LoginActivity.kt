package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewSignUp = findViewById(R.id.textViewSignUp)

        buttonLogin.setOnClickListener { loginUser() }
        textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        // Dummy login check (gunakan email: "user@test.com" dan password: "123456" sebagai dummy)
        if (email == "user@test.com" && password == "123456") {
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
        }
    }
}
