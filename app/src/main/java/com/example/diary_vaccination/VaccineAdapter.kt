package com.example.diary_vaccination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diary_vaccination.database.Vaccine

class VaccineAdapter: RecyclerView.Adapter<VaccineViewHolder>() {
    var data = listOf<Vaccine>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_item_view_vaccine,
            parent, false)

        return VaccineViewHolder(view)
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.resources
        holder.vaccinationName.text = item.vaccineName
        if (item.vaccineComponent)
            holder.vaccinationComponent.text = "2"
        else
            holder.vaccinationComponent.text = "1"
    }

    override fun getItemCount(): Int {
        return data.size
    }
}