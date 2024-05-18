package com.example.exerwise.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exerwise.WorkoutItemClickListener
import com.example.exerwise.data.model.Workout
import com.example.exerwise.databinding.ItemFinishedWorkoutBinding

class FinishedWorkoutAdapter : ListAdapter<Workout, FinishedWorkoutAdapter.ViewHolder>(FinishedWorkoutDiffCallback()) {

    private var itemClickListener: WorkoutItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFinishedWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            itemClickListener?.onWorkoutItemClick(getItem(position))
        }
    }

    fun setItemClickListener(listener: WorkoutItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(private val binding: ItemFinishedWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            binding.workoutName.text = workout.name
        }
    }

    class FinishedWorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }
    }
}