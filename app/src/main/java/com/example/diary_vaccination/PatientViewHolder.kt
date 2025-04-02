package com.example.diary_vaccination

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PatientViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val patientLastName: TextView = view.findViewById(R.id.patient_last_name)
    val patientName: TextView = view.findViewById(R.id.patient_name)
    val patientSurname: TextView = view.findViewById(R.id.patient_surname)
    val patientBirthday: TextView = view.findViewById(R.id.patient_birthday)

    val patientId: Button = view.findViewById(R.id.patient_id)

    var updatePatient: Button = view.findViewById(R.id.update_patient)
}