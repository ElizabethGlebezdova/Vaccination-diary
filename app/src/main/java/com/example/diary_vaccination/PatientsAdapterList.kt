package com.example.diary_vaccination

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.diary_vaccination.database.Patient

class PatientsAdapterList(val context: Context?): BaseAdapter() {
    var dataPatient = listOf<Patient>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return dataPatient.size
    }

    override fun getItem(p0: Int): Any {
        return dataPatient[p0]
    }

    override fun getItemId(p0: Int): Long {
        return dataPatient[p0].patientId
    }

    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.vaccine_spinner_view,
            p2, false)
        val text: TextView = view.findViewById(R.id.vaccination_adapter)
        text.text = dataPatient[p0].patientLastName + " " + dataPatient[p0].patientName
        return view
    }
}