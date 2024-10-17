package com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.if570_lab_uts_rifqiariefwicaksana_00000073943.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        val editTextNim = view.findViewById<EditText>(R.id.editTextNim)

        // Load saved profile data
        editTextName.setText(sharedPrefs.getString("name", ""))
        editTextNim.setText(sharedPrefs.getString("nim", ""))

        view.findViewById<Button>(R.id.buttonSaveProfile).setOnClickListener {
            val name = editTextName.text.toString()
            val nim = editTextNim.text.toString()

            sharedPrefs.edit().apply {
                putString("name", name)
                putString("nim", nim)
                apply()
            }

            Toast.makeText(context, "Profile saved", Toast.LENGTH_SHORT).show()
        }
    }
}
