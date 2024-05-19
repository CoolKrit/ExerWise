package com.example.exerwise.presentation.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.exerwise.R
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.Workout
import com.example.exerwise.databinding.FragmentStartWorkoutBinding
import com.example.exerwise.presentation.adapter.StartWorkoutAdapter
import com.example.exerwise.presentation.viewmodel.CreateWorkoutViewModel
import com.example.exerwise.utils.FirebaseUtils
import kotlinx.coroutines.launch
import java.util.Date

class StartWorkoutFragment : Fragment() {

    private var _binding: FragmentStartWorkoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateWorkoutViewModel by viewModels()

    private var startTime = 0L
    private var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workout = arguments?.getParcelable<Workout>("workout")
        if (workout != null) {
            viewModel.setWorkout(workout)
        }

        val recyclerView = binding.recyclerViewExercises
        val adapter = StartWorkoutAdapter(viewModel) {
            showDialogFragment(it)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.workoutName.text = viewModel.workout.value!!.name
        binding.textViewDuration.text = viewModel.workout.value!!.duration
        binding.textViewTotalWeight.text = viewModel.workout.value!!.volume
        binding.textViewTotalSets.text = viewModel.workout.value!!.sets

        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            if (exercises != null) {
                adapter.submitList(exercises)
            }
        }

        binding.closeButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.saveButton.setOnClickListener {
            saveWorkoutToFirebase(
                Workout(
                    Date().toString(),
                    viewModel.workout.value!!.name,
                    binding.textViewDuration.text.toString(),
                    binding.textViewTotalWeight.text.toString(),
                    binding.textViewTotalSets.text.toString(),
                    viewModel.exercises.value!!
                )
            )
            findNavController().popBackStack()
        }

        startTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        stopTimer()
    }

    private fun showDialogFragment(exercise: Exercise) {
        val dialogBinding = layoutInflater.inflate(R.layout.fragment_dialog_exercise_details, null)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        Glide.with(dialogBinding.findViewById<ImageView>(R.id.exerciseIV).context)
            .load(exercise.gifUrl)
            .into(dialogBinding.findViewById(R.id.exerciseIV))
        dialogBinding.findViewById<TextView>(R.id.textViewName).text = exercise.name
        dialogBinding.findViewById<TextView>(R.id.textViewBodyPart).text = "Body part: ${exercise.bodyPart}"
        dialogBinding.findViewById<TextView>(R.id.textViewTarget).text = "Target muscle: ${exercise.target}"
        dialogBinding.findViewById<TextView>(R.id.textViewEquipment).text = "Equipment: ${exercise.equipment}"
        var instructions = ""
        var index = 1
        exercise.instructions.forEach { i ->
            instructions += if (i == exercise.instructions.last()) "${index++}. " + i else "${index++}. " + i + "\n"
        }
        dialogBinding.findViewById<TextView>(R.id.textViewInstructions).text = instructions
    }

    private fun startTimer() {
        startTime = System.currentTimeMillis()
        runnable = object : Runnable {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                val elapsedTimeInSeconds = (currentTime - startTime) / 1000

                val hours = elapsedTimeInSeconds / 3600
                val minutes = (elapsedTimeInSeconds % 3600) / 60
                val seconds = elapsedTimeInSeconds % 60

                val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                binding.textViewDuration.text = formattedTime

                var totalWeight = 0.0
                var totalSets = 0
                viewModel.exercises.value?.forEach { exercise ->
                    exercise.exerciseSetsList.forEach { set ->
                        totalWeight += set.setWeight * set.setReps
                        totalSets += 1
                    }
                }

                binding.textViewTotalWeight.text = totalWeight.toString()
                binding.textViewTotalSets.text = totalSets.toString()

                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    private fun saveWorkoutToFirebase(workout: Workout) {
        lifecycleScope.launch {
            try {
                FirebaseUtils.finishWorkout(workout)
                Toast.makeText(requireContext(), "Workout saved successfully", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to save workout", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}