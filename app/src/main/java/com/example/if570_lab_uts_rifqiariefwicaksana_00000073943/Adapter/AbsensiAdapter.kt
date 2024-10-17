package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Models.Absensi
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class AbsensiAdapter(private val attendanceList: List<Absensi>) :
    RecyclerView.Adapter<AbsensiAdapter.AbsensiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsensiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_absensi, parent, false)
        return AbsensiViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbsensiViewHolder, position: Int) {
        val absensi = attendanceList[position]
        holder.textViewTanggal.text = absensi.dateTime

        // Convert ByteArray to Bitmap and display it
        val photoBitmap = BitmapFactory.decodeByteArray(absensi.photoBytes, 0, absensi.photoBytes.size)
        holder.imageViewPhoto.setImageBitmap(photoBitmap)
    }

    override fun getItemCount(): Int = attendanceList.size

    class AbsensiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTanggal: TextView = itemView.findViewById(R.id.textViewTanggal)
        val imageViewPhoto: ImageView = itemView.findViewById(R.id.imageViewPhoto)
    }
}
