package com.example.diary_vaccination

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.diary_vaccination.database.VaccinationDatabase
import com.example.diary_vaccination.databinding.FragmentAddNewEntryBinding
import java.util.Calendar

private lateinit var viewModelVaccine: VaccineViewModel
private lateinit var viewModelEntry: EntryViewModel
private lateinit var viewModelPatient: PatientViewModel

class AddNewEntryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val dao = VaccinationDatabase.getInstance(application).getDiaryVaccinationDao()
        val viewModelVaccineFactory = VaccineViewModelFactory(dao, application)
        val viewModelEntryFactory = EntryViewModelFactory(dao, application)
        val viewModelPatientFactory = PatientViewModelFactory(dao, application)

        viewModelVaccine = ViewModelProvider(this, viewModelVaccineFactory)[VaccineViewModel::class.java]
        viewModelEntry = ViewModelProvider(this, viewModelEntryFactory)[EntryViewModel::class.java]
        viewModelPatient = ViewModelProvider(this, viewModelPatientFactory)[PatientViewModel::class.java]

        val binding = DataBindingUtil.inflate<FragmentAddNewEntryBinding>(
            inflater, R.layout.fragment_add_new_entry, container, false)

        val toastAddNewEntry = "Entry added"
        val toastError = "Fields is not filled. Try again"
        val duration = Toast.LENGTH_SHORT

        val adapterVaccines = VaccineAdapterList(application)
        val adapterPatients = PatientsAdapterList(application)

        binding.vaccinationId.adapter = adapterVaccines
        binding.patientId.adapter = adapterPatients

        viewModelVaccine.vaccines.observe(viewLifecycleOwner, Observer { vaccines ->
            if (vaccines != null)
                adapterVaccines.dataVaccine = vaccines
        })
        viewModelPatient.patients.observe(viewLifecycleOwner, Observer { patients ->
            if (patients != null)
                adapterPatients.dataPatient = patients
        })

        binding.vaccinationId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedVaccine = adapterVaccines.dataVaccine[position]
                updateComponentSpinner(selectedVaccine.vaccineComponent, binding)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.addNewEntry.setOnClickListener {
            if (binding.vaccinationId.selectedItemId >= 0 &&
                binding.patientId.selectedItemId >= 0 &&
                binding.editTextDate.text.toString() != "" &&
                binding.editTextTime.text.toString() != "") {
                viewModelEntry.initNewEntry(
                    binding.patientId.selectedItemId,
                    binding.vaccinationId.selectedItemId,
                    binding.componentNumber.selectedItemId + 1,
                    binding.editTextDate.text.toString(),
                    binding.editTextTime.text.toString()
                )
                Toast.makeText(application, toastAddNewEntry, duration).show()
                binding.editTextDate.text.clear()
                binding.editTextTime.text.clear()
            } else {
                Toast.makeText(application, toastError, duration).show()
            }
        }

        binding.editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d.%02d.%d", dayOfMonth, month + 1, year)
                binding.editTextDate.setText(selectedDate)
            }
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis
            datePickerDialog.show()
        }

        binding.editTextTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val selectedDate = binding.editTextDate.text.toString()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                if (selectedDate == String.format("%02d.%02d.%d",
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.YEAR)
                    ) && (selectedHour > hour || (selectedHour == hour && selectedMinute > minute))) {
                    Toast.makeText(requireContext(), "Time error", Toast.LENGTH_SHORT).show()
                } else {
                    binding.editTextTime.setText(selectedTime)
                }
            }
            TimePickerDialog(requireContext(), timeSetListener, hour, minute, true).show()
        }

        return binding.root
    }

    private fun updateComponentSpinner(isTwoComponent: Boolean, binding: FragmentAddNewEntryBinding) {
        val componentsArray = if (isTwoComponent) arrayOf("1", "2") else arrayOf("1")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, componentsArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.componentNumber.adapter = adapter
    }
}
