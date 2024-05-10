package com.example.exerwise.presentation.fragment

import WorkoutAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerwise.R
import com.example.exerwise.data.model.Workout
import com.example.exerwise.databinding.FragmentWorkoutBinding
import com.example.exerwise.presentation.interfaces.WorkoutItemClickListener
import com.example.exerwise.presentation.viewmodel.WorkoutViewModel

class WorkoutFragment : Fragment() {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var workoutRVAdapter: WorkoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeTrainings()

        workoutRVAdapter.setItemClickListener(object : WorkoutItemClickListener {
            override fun onWorkoutItemClick(workout: Workout) {
                val bundle = bundleOf("workout" to workout)
                // findNavController().navigate(R.id.workoutDetailsFragment, bundle)
            }
        })

        binding.createWorkout.setOnClickListener {

            val navOptions = NavOptions.Builder().setPopUpTo(R.id.workoutFragment, true).build()
            findNavController().navigate(R.id.createWorkoutFragment, null, navOptions)
        }
    }

    private fun setupRecyclerView() {
        workoutRVAdapter = WorkoutAdapter()
        binding.workoutsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.workoutsRecyclerView.adapter = workoutRVAdapter
    }

    private fun observeTrainings() {
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
        workoutViewModel.workoutList.observe(viewLifecycleOwner, Observer { trainings ->
            workoutRVAdapter.submitList(trainings)
        })
    }
}