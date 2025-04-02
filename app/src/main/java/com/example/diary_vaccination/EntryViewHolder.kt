package com.example.diary_vaccination

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val entryLastName: TextView = view.findViewById(R.id.entry_last_name)
    val entryName: TextView = view.findViewById(R.id.entry_name)
    val entryBirthday: TextView = view.findViewById(R.id.entry_birthday)
    val entryVaccine: TextView = view.findViewById(R.id.entry_vaccine)
    val entryComponent: TextView = view.findViewById(R.id.entry_component)
    val entryDate: TextView = view.findViewById(R.id.entry_date)
    val entryTime: TextView = view.findViewById(R.id.entry_time)

    val entryId: Button = view.findViewById(R.id.entry_id)
    val updateEntry: Button = view.findViewById(R.id.update_entry)
}