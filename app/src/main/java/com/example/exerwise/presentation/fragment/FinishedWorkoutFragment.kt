package com.example.exerwise.presentation.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.exerwise.R
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.Workout
import com.example.exerwise.databinding.FragmentFinishedWorkoutBinding
import com.example.exerwise.presentation.adapter.FinishedWorkoutAdapterT
import com.example.exerwise.presentation.viewmodel.CreateWorkoutViewModel

class FinishedWorkoutFragment : Fragment() {

    private var _binding: FragmentFinishedWorkoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateWorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishedWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workout = arguments?.getParcelable<Workout>("workout")
        if (workout != null) {
            viewModel.setWorkout(workout)
        }

        val recyclerView = binding.recyclerViewExercises
        val adapter = FinishedWorkoutAdapterT(viewModel) {
            showDialogFragment(it)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.textViewDuration.text = viewModel.workout.value!!.duration
        binding.textViewTotalWeight.text = viewModel.workout.value!!.volume
        binding.textViewTotalSets.text = viewModel.workout.value!!.sets

        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            if (exercises != null) {
                adapter.submitList(exercises)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        exercise.instructions.forEach { i ->
            instructions += if (i == exercise.instructions.last()) i else i + "\n"
        }
        dialogBinding.findViewById<TextView>(R.id.textViewInstructions).text = instructions
    }
}