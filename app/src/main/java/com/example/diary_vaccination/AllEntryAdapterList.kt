package com.example.diary_vaccination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diary_vaccination.database.EntryAll

class AllEntryAdapterList(private val onClickListener: EntryOnClickListener):
    RecyclerView.Adapter<EntryViewHolder>() {
    var data = listOf<EntryAll>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_entry_view,
        parent, false)

        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.resources
        holder.entryLastName.text = item.patient_last_name
        holder.entryName.text = item.patient_name
        holder.entryBirthday.text = item.patient_birthday
        holder.entryVaccine.text = item.vaccine_name
        holder.entryComponent.text = item.component.toString()
        holder.entryDate.text = item.vaccine_date
        holder.entryTime.text = item.vaccine_time

        holder.entryId.setOnClickListener {
            onClickListener.entryOnClick(item.entry_id.toString())
        }

        holder.updateEntry.setOnClickListener {
            onClickListener.navigateEdit(item.entry_id.toString())
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}