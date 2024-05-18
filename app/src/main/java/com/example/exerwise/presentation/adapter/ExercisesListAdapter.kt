package com.example.exerwise.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exerwise.data.model.ExerciseResponse
import com.example.exerwise.databinding.ItemExercisesListBinding

class ExercisesListAdapter(private var exercises: List<ExerciseResponse>, private val onExerciseClick: (ExerciseResponse) -> Unit) : RecyclerView.Adapter<ExercisesListAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExercisesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount(): Int = exercises.size

    inner class ExerciseViewHolder(private val binding: ItemExercisesListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: ExerciseResponse) {
            binding.exerciseName.text = exercise.name
            binding.exerciseBodyPart.text = exercise.bodyPart
            Glide.with(binding.exerciseGif.context)
                .load(exercise.gifUrl)
                .into(binding.exerciseGif)
            binding.root.setOnClickListener { onExerciseClick(exercise) }
        }
    }
}