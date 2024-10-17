package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views using findViewById
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextNim = findViewById<EditText>(R.id.editTextNim)
        val buttonSaveProfile = findViewById<Button>(R.id.buttonSaveProfile)

        // Access shared preferences
        val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Load saved profile data
        editTextName.setText(sharedPrefs.getString("name", ""))
        editTextNim.setText(sharedPrefs.getString("nim", ""))

        // Save profile data when button is clicked
        buttonSaveProfile.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val nim = editTextNim.text.toString().trim()

            // Input validation to ensure fields are not empty
            if (name.isEmpty() || nim.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Save data to SharedPreferences
                sharedPrefs.edit().apply {
                    putString("name", name)
                    putString("nim", nim)
                    apply()
                }

                // Show confirmation message
                Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show()

                // Optionally, disable the save button briefly to avoid multiple clicks
                buttonSaveProfile.isEnabled = false
                buttonSaveProfile.postDelayed({ buttonSaveProfile.isEnabled = true }, 1000) // Re-enable after 1 second
            }
        }
    }
}
