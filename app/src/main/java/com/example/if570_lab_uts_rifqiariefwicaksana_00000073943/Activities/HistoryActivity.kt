package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Activities

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Models.Absensi
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Adapter.AbsensiAdapter

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerViewRiwayat: RecyclerView
    private lateinit var absensiAdapter: AbsensiAdapter
    private val dummyData = mutableListOf<Absensi>()
}
