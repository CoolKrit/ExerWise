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
        val textView: TextView = binding.exerciseTitle
        val textViewMus: TextView = binding.exerciseMuscle
        var exerciseSets: MutableList<ExerciseSet> = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position].name
        holder.textViewMus.text = data[position].muscle
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