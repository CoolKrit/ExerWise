package com.example.exerwise.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.exerwise.R
import com.example.exerwise.databinding.FragmentSignInBinding
import com.example.exerwise.databinding.FragmentWorkoutBinding
import com.example.exerwise.presentation.adapter.WorkoutAdapter
import com.example.exerwise.presentation.viewmodel.WorkoutViewModel

class WorkoutFragment : Fragment() {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]

        val adapter = WorkoutAdapter()
        binding.workoutsRecyclerView.adapter = adapter

        workoutViewModel.workouts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}