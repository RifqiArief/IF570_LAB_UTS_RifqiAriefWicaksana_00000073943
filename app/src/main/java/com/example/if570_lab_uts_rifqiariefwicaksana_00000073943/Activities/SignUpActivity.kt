package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignUp = findViewById(R.id.buttonSignUp)

        buttonSignUp.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
            Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}
