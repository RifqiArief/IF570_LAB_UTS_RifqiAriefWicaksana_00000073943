package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Adapter.AbsensiAdapter
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Models.Absensi
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class HistoryActivity : AppCompatActivity() {

    private fun byteArrayToBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Save the last visited page as HistoryActivity
        saveLastPage("HistoryActivity")

        // Retrieve attendance list from the Intent
        val attendanceList = intent.getSerializableExtra("attendanceList") as? ArrayList<Absensi>
            ?: arrayListOf()

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRiwayat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AbsensiAdapter(attendanceList)

        // Handle the case where attendance list is empty
        if (attendanceList.isEmpty()) {
            Toast.makeText(this, "No attendance records found", Toast.LENGTH_SHORT).show()
        }
    }

    // Save the last visited page in SharedPreferences
    private fun saveLastPage(pageName: String) {
        val sharedPrefs = getSharedPreferences("LastPagePrefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putString("lastPage", pageName).apply()
    }

    // Properly handle back press with a call to super
    override fun onBackPressed() {
        // Navigate back to HomeActivity when back button is pressed
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()

        // Call the super method to respect the base class behavior
        super.onBackPressed()
    }
}
