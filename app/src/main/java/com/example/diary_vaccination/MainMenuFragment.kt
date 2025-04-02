package com.example.diary_vaccination

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.diary_vaccination.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentMainMenuBinding>(
            inflater, R.layout.fragment_main_menu, container, false)

        binding.apply {
            settingsMenu.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_mainMenuFragment_to_settingsMenuFragment)
            }
            newPatient.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_mainMenuFragment_to_addNewPatientFragment)
            }
            newEntry.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_mainMenuFragment_to_addNewEntryFragment)
            }
            selectAllPatients.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_mainMenuFragment_to_selectPatientFragment)
            }
            selectAllEntry.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_mainMenuFragment_to_selectEntryFragment)
            }
        }

        return binding.root
    }
}