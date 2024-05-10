package com.example.exerwise.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.ExerciseSet
import com.example.exerwise.databinding.ItemExerciseListBinding
import com.example.exerwise.presentation.interfaces.ExerciseListItemClickListener

class ExerciseListAdapter(private var data: List<Exercise>) : RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    private var itemClickListener: ExerciseListItemClickListener? = null

    inner class ViewHolder(binding: ItemExerciseListBinding) : RecyclerView.ViewHolder(binding.root) {
        val exerciseTitle: TextView = binding.exerciseTitle
        val exerciseMuscle: TextView = binding.exerciseMuscle
        val exerciseInstructions: TextView = binding.exerciseInstructions
        var exerciseSets: MutableList<ExerciseSet> = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.exerciseTitle.text = data[position].name
        holder.exerciseMuscle.text = data[position].muscle
        holder.exerciseInstructions.text = data[position].instructions
        holder.exerciseSets = data[position].sets

        // Устанавливаем слушатель кликов на элемент списка
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(data[position])
        }
    }

    fun updateExercises(newExercises: List<Exercise>) {
        data = newExercises
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // Метод для установки слушателя кликов
    fun setOnItemClickListener(listener: ExerciseListItemClickListener) {
        this.itemClickListener = listener
    }
}