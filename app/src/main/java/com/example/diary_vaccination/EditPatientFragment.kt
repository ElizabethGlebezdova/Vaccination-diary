package com.example.diary_vaccination

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diary_vaccination.database.VaccinationDatabase
import com.example.diary_vaccination.databinding.FragmentEditPatientBinding
import java.util.Calendar

private lateinit var viewModelPatient: PatientViewModel

class EditPatientFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val dao = VaccinationDatabase.getInstance(application).getDiaryVaccinationDao()
        val viewModelPatientFactory = PatientViewModelFactory(dao, application)
        viewModelPatient = ViewModelProvider(this, viewModelPatientFactory)[PatientViewModel::class.java]

        val binding = DataBindingUtil.inflate<FragmentEditPatientBinding>(
            inflater, R.layout.fragment_edit_patient, container, false
        )

        val toastText = "The Patient has been edited"
        val toastError = "Fields is not filled. Try again"
        val duration = Toast.LENGTH_SHORT

        val args = EditPatientFragmentArgs.fromBundle(requireArguments())

        viewModelPatient.getPatientById(args.patientId).observe(viewLifecycleOwner, Observer { patient ->
            patient?.let {
                binding.Name.setText(it.patientName)
                binding.LastName.setText(it.patientLastName)
                binding.Surname.setText(it.patientSurname)
                binding.Birthday.setText(it.patientBirthday)
            }
        })

        binding.savePatient.setOnClickListener {
            if (binding.Name.text.toString() != "" &&
                binding.LastName.text.toString() != "" &&
                binding.Surname.text.toString() != "" &&
                binding.Birthday.text.toString() != ""
            ) {
                viewModelPatient.updatePatient(
                    args.patientId,
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
                findNavController().navigateUp()
            } else {
                Toast.makeText(application, toastError, duration).show()
            }
        }

        binding.Birthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d.%02d.%d", dayOfMonth, month + 1, year)
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
