package com.example.exerwise.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.Set
import com.example.exerwise.databinding.ItemExerciseStartWorkoutBinding
import com.example.exerwise.presentation.viewmodel.CreateWorkoutViewModel

class StartWorkoutAdapter(private val viewModel: CreateWorkoutViewModel, private val onExerciseClick: (Exercise) -> Unit) : RecyclerView.Adapter<StartWorkoutAdapter.ExerciseViewHolder>() {

    private val exercises = mutableListOf<Exercise>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseStartWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount(): Int = exercises.size

    fun submitList(newExercises: List<Exercise>) {
        exercises.clear()
        exercises.addAll(newExercises)
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(
        private val binding: ItemExerciseStartWorkoutBinding,
        private val viewModel: CreateWorkoutViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.root.tag = exercise
            binding.exerciseName.text = exercise.name
            binding.exerciseBodyPart.text = exercise.bodyPart
            Glide.with(binding.exerciseIV.context)
                .load(exercise.gifUrl)
                .into(binding.exerciseIV)

            val setsAdapter = SetsAdapter(exercise, viewModel)
            binding.exerciseSetsRV.layoutManager = LinearLayoutManager(binding.root.context)
            binding.exerciseSetsRV.adapter = setsAdapter
            setsAdapter.submitList(exercise.exerciseSetsList)
            binding.exerciseAddSet.setOnClickListener {
                val exercise = binding.root.tag as Exercise
                val newSet = Set(setsAdapter.currentList.size + 1, 0.0, 0)
                viewModel.addSet(exercise.id, newSet)
            }

            binding.exerciseIV.setOnClickListener { onExerciseClick(exercise) }
        }
    }
}