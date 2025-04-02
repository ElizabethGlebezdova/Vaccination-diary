package com.example.diary_vaccination

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.diary_vaccination.database.Vaccine

class VaccineAdapterList(val context: Context?) : BaseAdapter() {
    var dataVaccine = listOf<Vaccine>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return dataVaccine.size
    }

    override fun getItem(p0: Int): Any {
        return dataVaccine[p0]
    }

    override fun getItemId(p0: Int): Long {
        return dataVaccine[p0].vaccineId
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.vaccine_spinner_view,
            p2, false)
        val text: TextView = view.findViewById(R.id.vaccination_adapter)
        text.text = dataVaccine[p0].vaccineName
        return view
    }
}