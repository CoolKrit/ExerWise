package com.example.exerwise.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.databinding.ItemExerciseFinishedWorkoutBinding
import com.example.exerwise.presentation.viewmodel.CreateWorkoutViewModel

class FinishedWorkoutAdapterT(private val viewModel: CreateWorkoutViewModel, private val onExerciseClick: (Exercise) -> Unit) : RecyclerView.Adapter<FinishedWorkoutAdapterT.ExerciseViewHolder>() {

    private val exercises = mutableListOf<Exercise>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseFinishedWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        private val binding: ItemExerciseFinishedWorkoutBinding,
        private val viewModel: CreateWorkoutViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.root.tag = exercise
            binding.exerciseName.text = exercise.name
            binding.exerciseBodyPart.text = exercise.bodyPart
            Glide.with(binding.exerciseIV.context)
                .load(exercise.gifUrl)
                .into(binding.exerciseIV)

            val setsAdapter = FinishedSetsAdapter()
            binding.exerciseSetsRV.layoutManager = LinearLayoutManager(binding.root.context)
            binding.exerciseSetsRV.adapter = setsAdapter
            setsAdapter.submitList(exercise.exerciseSetsList)

            binding.exerciseIV.setOnClickListener { onExerciseClick(exercise) }
        }
    }
}