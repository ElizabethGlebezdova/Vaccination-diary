package com.example.diary_vaccination

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.diary_vaccination.database.VaccinationDatabase
import com.example.diary_vaccination.databinding.FragmentAddNewPatientBinding
import java.util.Calendar

class AddNewPatientFragment : Fragment() {

    private lateinit var viewModel: PatientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentAddNewPatientBinding>(
            inflater, R.layout.fragment_add_new_patient, container, false)

        val application = requireNotNull(this.activity).application
        val dao = VaccinationDatabase.getInstance(application).getDiaryVaccinationDao()
        val viewModelFactory = PatientViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[PatientViewModel::class.java]

        val toastText = "The Patient has been added"
        val toastError =  "Fields is not filled. Try again"
        val duration = Toast.LENGTH_SHORT

        binding.saveNewPatient.setOnClickListener {
            if (binding.Name.text.toString() != "" &&
                binding.LastName.text.toString() != "" &&
                binding.Surname.text.toString() != "" &&
                binding.Birthday.text.toString() != ""){
                viewModel.initNewPatient(
                    binding.Name.text.toString(),
                    binding.LastName.text.toString(),
                    binding.Surname.text.toString(),
                    binding.Birthday.text.toString()
                )
                Toast.makeText(application, toastText, duration).show()
                binding.Name.text.clear()
                binding.LastName.text.clear()
                binding.Surname.text.clear()
                binding.Birthday.text.clear()

            } else {
                Toast.makeText(application, toastError, duration).show()
            }
        }

        binding.Birthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)

                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                binding.Birthday.setText(selectedDate)
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


        return binding.root
    }
}