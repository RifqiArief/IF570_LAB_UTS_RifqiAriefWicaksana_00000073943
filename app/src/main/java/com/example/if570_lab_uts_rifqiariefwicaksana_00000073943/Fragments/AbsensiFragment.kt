package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Models.Absensi
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities.HistoryActivity
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AbsensiFragment : Fragment() {

    private val attendanceList = mutableListOf<Absensi>()

    companion object {
        const val CAMERA_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_absensi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonAbsen = view.findViewById<Button>(R.id.buttonAbsen)
        val buttonHistory = view.findViewById<Button>(R.id.buttonHistory)

        // Handle Absen button click
        buttonAbsen.setOnClickListener {
            openCamera()
        }

        // Navigate to HistoryActivity with attendance list
        buttonHistory.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)
            intent.putExtra("attendanceList", ArrayList(attendanceList))
            startActivity(intent)
        }
    }

    // Open the camera to capture a photo
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    // Convert Bitmap to ByteArray
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    // Handle the captured photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            val photoBytes = bitmapToByteArray(photo)

            val currentDateTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

            // Add the photoBytes and timestamp to the attendance list
            attendanceList.add(Absensi(currentDateTime, photoBytes))

            Toast.makeText(context, "Absen berhasil!", Toast.LENGTH_SHORT).show()
        }
    }
}
