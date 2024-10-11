package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class HomeActivity : AppCompatActivity() {

    private lateinit var textViewWelcome: TextView
    private lateinit var buttonAbsen: Button
    private lateinit var buttonLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        textViewWelcome = findViewById(R.id.textViewWelcome)
        buttonAbsen = findViewById(R.id.buttonAbsen)
        buttonLogout = findViewById(R.id.buttonLogout)

        // Dummy data untuk nama pengguna
        val userName = "User"
        textViewWelcome.text = "Selamat Datang, $userName"

        buttonAbsen.setOnClickListener {
            Toast.makeText(this, "Absensi dilakukan (dummy)", Toast.LENGTH_SHORT).show()
        }

        buttonLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
