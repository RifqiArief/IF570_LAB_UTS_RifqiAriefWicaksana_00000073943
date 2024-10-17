package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Models.Absensi
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    private val attendanceList = mutableListOf<Absensi>()
    private var currentPhoto: Bitmap? = null

    companion object {
        const val CAMERA_REQUEST_CODE = 100
        const val CAMERA_PERMISSION_CODE = 101
    }

    private lateinit var imageViewPhoto: ImageView
    private lateinit var buttonRetake: Button
    private lateinit var buttonConfirm: Button
    private lateinit var buttonAbsen: Button
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check login state, redirect if necessary
        sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)
        if (!isLoggedIn) {
            redirectToLogin()
            return
        }

        setContentView(R.layout.activity_home)

        // Save the current page as HomeActivity for state preservation
        saveLastPage("HomeActivity")

        // Initialize views
        val textViewWelcome = findViewById<TextView>(R.id.textViewWelcome)
        val textViewDateTime = findViewById<TextView>(R.id.textViewDateTime)
        buttonAbsen = findViewById(R.id.buttonAbsen)
        val buttonHistory = findViewById<Button>(R.id.buttonHistory)
        val buttonLogout = findViewById<Button>(R.id.buttonLogout)
        imageViewPhoto = findViewById(R.id.imageViewPhoto)
        buttonRetake = findViewById(R.id.buttonRetake)
        buttonConfirm = findViewById(R.id.buttonConfirm)

        // Hide photo-related views initially
        imageViewPhoto.visibility = ImageView.GONE
        buttonRetake.visibility = Button.GONE
        buttonConfirm.visibility = Button.GONE

        textViewWelcome.text = "Selamat Datang, User"

        // Start updating the date and time
        updateDateTime(textViewDateTime)

        // Check if the user can perform attendance
        updateAbsenButtonState()

        buttonAbsen.setOnClickListener {
            if (checkAndRequestPermissions()) {
                openCamera()
            }
        }

        buttonRetake.setOnClickListener {
            openCamera()
        }

        buttonConfirm.setOnClickListener {
            confirmAttendance()
        }

        buttonHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("attendanceList", ArrayList(attendanceList))
            startActivity(intent)
        }

        buttonLogout.setOnClickListener {
            logout()
        }
    }

    private fun saveLastPage(pageName: String) {
        val lastPagePrefs = getSharedPreferences("LastPagePrefs", Context.MODE_PRIVATE)
        lastPagePrefs.edit().putString("lastPage", pageName).apply()
    }

    private fun updateDateTime(textView: TextView) {
        val handler = android.os.Handler(mainLooper)
        val dateTimeFormat = SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm:ss", Locale.getDefault())

        handler.post(object : Runnable {
            override fun run() {
                textView.text = dateTimeFormat.format(Date())
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun updateAbsenButtonState() {
        val lastAbsenDate = sharedPrefs.getString("lastAbsenDate", "")
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val hasCheckedIn = sharedPrefs.getBoolean("hasCheckedIn", false)
        val hasCheckedOut = sharedPrefs.getBoolean("hasCheckedOut", false)

        if (lastAbsenDate == currentDate) {
            when {
                hasCheckedIn && !hasCheckedOut -> buttonAbsen.text = "Absen Pulang"
                hasCheckedOut -> {
                    buttonAbsen.isEnabled = false
                    buttonAbsen.text = "Sudah Absen Hari Ini"
                }
            }
        } else {
            sharedPrefs.edit().apply {
                putBoolean("hasCheckedIn", false)
                putBoolean("hasCheckedOut", false)
                apply()
            }
            buttonAbsen.text = "Absen Sekarang"
        }
    }

    private fun confirmAttendance() {
        currentPhoto?.let { photo ->
            val photoBytes = bitmapToByteArray(photo)
            val currentDateTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

            val isCheckedIn = sharedPrefs.getBoolean("hasCheckedIn", false)

            if (!isCheckedIn) {
                attendanceList.add(Absensi(currentDateTime, photoBytes))
                sharedPrefs.edit().apply {
                    putBoolean("hasCheckedIn", true)
                    putString("lastAbsenDate", SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()))
                    apply()
                }
                Toast.makeText(this, "Absen Masuk Berhasil!", Toast.LENGTH_SHORT).show()
                buttonAbsen.text = "Absen Pulang"
            } else {
                attendanceList.add(Absensi(currentDateTime, photoBytes))
                sharedPrefs.edit().apply {
                    putBoolean("hasCheckedOut", true)
                    apply()
                }
                Toast.makeText(this, "Absen Pulang Berhasil!", Toast.LENGTH_SHORT).show()
                buttonAbsen.isEnabled = false
                buttonAbsen.text = "Sudah Absen Hari Ini"
            }

            imageViewPhoto.visibility = ImageView.GONE
            buttonRetake.visibility = Button.GONE
            buttonConfirm.visibility = Button.GONE
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            currentPhoto = photo

            imageViewPhoto.setImageBitmap(photo)
            imageViewPhoto.visibility = ImageView.VISIBLE
            buttonRetake.visibility = Button.VISIBLE
            buttonConfirm.visibility = Button.VISIBLE
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun logout() {
        sharedPrefs.edit().clear().apply()
        redirectToLogin()
    }
}
