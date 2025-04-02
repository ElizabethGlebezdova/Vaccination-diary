package com.example.diary_vaccination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diary_vaccination.database.Patient

class AllPatientAdapterList(private val onClickListener: PatientOnClickListener) :
    RecyclerView.Adapter<PatientViewHolder>() {
    var data = listOf<Patient>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.select_patient_view,
            parent, false
        )

        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.resources
        holder.patientLastName.text = item.patientLastName
        holder.patientName.text = item.patientName
        holder.patientSurname.text = item.patientSurname
        holder.patientBirthday.text = item.patientBirthday

        holder.patientId.setOnClickListener {
            onClickListener.patientOnClick(item.patientId.toString())
        }
        holder.updatePatient.setOnClickListener {
            onClickListener.navigatePatientEdit(item.patientId.toString())
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}