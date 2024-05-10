package com.example.exerwise.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerwise.R
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.Workout
import com.example.exerwise.databinding.FragmentCreateWorkoutBinding
import com.example.exerwise.presentation.adapter.CreateWorkoutAdapter
import com.example.exerwise.presentation.viewmodel.ExerciseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class CreateWorkoutFragment : Fragment() {
    private var _binding: FragmentCreateWorkoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var exerciseViewModel: ExerciseViewModel
    private var dataList: MutableList<Exercise> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCreateWorkoutBinding.inflate(inflater, container, false)
        exerciseViewModel = ViewModelProvider(requireActivity())[ExerciseViewModel::class.java]

        val adapter = CreateWorkoutAdapter(exerciseViewModel.selectedExercises)
        binding.exercisesRV.layoutManager = LinearLayoutManager(requireContext())
        binding.exercisesRV.adapter = adapter

        binding.workoutTitle.setText(exerciseViewModel.selectedTitle)

        dataList.forEach {
            it.sets = adapter.dataSets
            exerciseViewModel.addExercise(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveCreateWorkout.setOnClickListener {
            createWorkout(Workout(Date().toString(), binding.workoutTitle.text.toString(), exerciseViewModel.selectedExercises))
            exerciseViewModel.clear()
            findNavController().navigate(R.id.workoutFragment)
        }

        binding.closeCreateWorkout.setOnClickListener {
            exerciseViewModel.clear()
            findNavController().navigate(R.id.workoutFragment)
        }

        binding.addExerciseButton.setOnClickListener {
            exerciseViewModel.addTitle(binding.workoutTitle.text.toString())
            findNavController().navigate(R.id.exerciseListFragment)
        }
    }

    fun createWorkout(workout: Workout) {
        val workoutMap = hashMapOf(
            "id" to workout.id,
            "name" to workout.name,
            "exercises" to workout.exercises
        )
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("workouts").document(workout.id).set(workoutMap)
    }
}