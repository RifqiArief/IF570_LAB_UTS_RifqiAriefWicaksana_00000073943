package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Access SharedPreferences safely
            val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)

            // Check if the user visited a page previously
            val lastPagePrefs = getSharedPreferences("LastPagePrefs", Context.MODE_PRIVATE)
            val lastPage = lastPagePrefs.getString("lastPage", "")

            // Redirect based on login status and last page visited
            val intent = when {
                isLoggedIn && !lastPage.isNullOrEmpty() -> getLastPageIntent(lastPage)
                isLoggedIn -> Intent(this, HomeActivity::class.java)
                else -> Intent(this, LoginActivity::class.java)
            }

            startActivity(intent)
        } catch (e: Exception) {
            // Log the error for debugging
            Log.e("LauncherActivity", "Error starting activity: ${e.message}")
            // Fallback to LoginActivity in case of any error
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Finish LauncherActivity so it doesn’t stay on the back stack
        finish()
    }

    // Function to return the appropriate intent for the last visited page
    private fun getLastPageIntent(lastPage: String): Intent {
        return when (lastPage) {
            "HomeActivity" -> Intent(this, HomeActivity::class.java)
            "HistoryActivity" -> Intent(this, HistoryActivity::class.java)
            "ProfileActivity" -> Intent(this, ProfileActivity::class.java)
            else -> Intent(this, HomeActivity::class.java) // Default to Home if no match found
        }
    }
}
