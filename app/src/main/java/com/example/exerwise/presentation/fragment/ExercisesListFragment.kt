package com.example.exerwise.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerwise.R
import com.example.exerwise.databinding.FragmentExercisesListBinding
import com.example.exerwise.presentation.adapter.ExercisesListAdapter
import com.example.exerwise.presentation.viewmodel.ExercisesListViewModel
import com.example.exerwise.presentation.viewmodel.SharedViewModel

class ExercisesListFragment : Fragment() {

    private var _binding: FragmentExercisesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExercisesListViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercisesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        val recyclerView = binding.exercisesListRV
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            recyclerView.adapter = ExercisesListAdapter(exercises) { exercise ->
                sharedViewModel.selectExercise(exercise)
                findNavController().navigateUp()
            }
            binding.noResultsText.visibility = if (exercises.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
        }

        // Setup Search
        binding.exercisesListSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterExercises()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterExercises()
                return false
            }
        })

        // Setup Spinners
        setupSpinners()

        viewModel.fetchExercises()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSpinners() {
        // Set up the Body Part Spinner
        val bodyParts = resources.getStringArray(R.array.body_part_array)
        val bodyPartAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bodyParts)
        bodyPartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBodyPart.adapter = bodyPartAdapter

        // Set up the Equipment Spinner
        val equipments = resources.getStringArray(R.array.equipment_array)
        val equipmentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, equipments)
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerEquipment.adapter = equipmentAdapter

        // Set listeners to update the list when a new item is selected
        binding.spinnerBodyPart.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterExercises()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerEquipment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterExercises()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun filterExercises() {
        val query = binding.exercisesListSV.query.toString().lowercase()
        val bodyPart = binding.spinnerBodyPart.selectedItem.toString().lowercase()
        val equipment = binding.spinnerEquipment.selectedItem.toString().lowercase()

        if (bodyPart == "all" && equipment == "all") {
            viewModel.fetchExercises()
        } else if (bodyPart == "all") {
            viewModel.fetchExercisesFiltered(query, "", equipment)
        } else if (equipment == "all") {
            viewModel.fetchExercisesFiltered(query, bodyPart, "")
        } else {
            viewModel.fetchExercisesFiltered(query, bodyPart, equipment)
        }
    }
}