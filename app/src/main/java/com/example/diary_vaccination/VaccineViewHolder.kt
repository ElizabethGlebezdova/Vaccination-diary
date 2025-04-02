package com.example.diary_vaccination

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VaccineViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val vaccinationName: TextView = view.findViewById(R.id.vaccination_name)
    val vaccinationComponent: TextView = view.findViewById(R.id.vaccination_component)
}