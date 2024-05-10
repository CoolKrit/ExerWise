package com.example.exerwise.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.exerwise.R
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.ExerciseSet
import com.example.exerwise.databinding.ItemExerciseWorkoutBinding

class CreateWorkoutAdapter(private var data: List<Exercise>) : RecyclerView.Adapter<CreateWorkoutAdapter.ViewHolder>() {
    var dataSets: MutableList<ExerciseSet> = mutableListOf()

    inner class ViewHolder(binding: ItemExerciseWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.exerciseTitle
        val exerciseSetsContainer: LinearLayout = binding.exerciseSetsContainer
        val setButton: Button = binding.exerciseAddSet

        fun addSetView(setNumber: Int, exercise: Exercise) {
            val setView = LayoutInflater.from(itemView.context).inflate(R.layout.item_exercise_set, exerciseSetsContainer, false)
            val textSetNumber: TextView = setView.findViewById(R.id.setNumberTextView)
            val editWeight: EditText = setView.findViewById(R.id.weightEditText)
            val editReps: EditText = setView.findViewById(R.id.repsEditText)
            val deleteSetButton: ImageButton = setView.findViewById(R.id.deleteSetButton)

            textSetNumber.text = "$setNumber"

            // Устанавливаем сохраненные значения веса и повторений, если они уже были введены
            val currentSet = exercise.sets.find { it.setNumber == setNumber }
            if (currentSet != null) {
                editWeight.setText(currentSet.weight)
                editReps.setText(currentSet.reps)
            }

            // Слушатель изменений в поле веса
            editWeight.addTextChangedListener {
                val weightText = it.toString()
                if (weightText.isNotEmpty()) {
                    exercise.sets.find { it.setNumber == setNumber }?.weight = weightText
                    Log.e("TAG", "addSetView: ${exercise.sets.find { it.setNumber == setNumber }?.weight}", )
                }
            }

            // Слушатель изменений в поле повторений
            editReps.addTextChangedListener {
                val repsText = it.toString()
                if (repsText.isNotEmpty()) {
                    exercise.sets.find { it.setNumber == setNumber }?.reps = repsText
                    Log.e("TAG", "addSetView: ${exercise.sets.find { it.setNumber == setNumber }?.reps}")
                }
            }

            // Обработчик нажатия на кнопку удаления подхода
            deleteSetButton.setOnClickListener {
                // Удаляем подход из списка exercise.sets
                exercise.sets.remove(currentSet)

                // Удаляем view подхода из контейнера
                exerciseSetsContainer.removeView(setView)

                // Обновляем номера подходов в тексте для оставшихся подходов
                exerciseSetsContainer.children.forEachIndexed { index, view ->
                    val setTextView: TextView = view.findViewById(R.id.setNumberTextView)
                    setTextView.text = "${index + 1}"
                    exercise.sets[index].setNumber = index+1
                }
            }

            // Добавляем view подхода в контейнер
            exerciseSetsContainer.addView(setView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position].name
        holder.exerciseSetsContainer.removeAllViews()

        data[position].sets.forEach { set ->
            holder.addSetView(set.setNumber, data[position])
        }

        holder.setButton.setOnClickListener {
            val setNumber = data[position].sets.size + 1
            data[position].sets.add(ExerciseSet(setNumber, "", ""))
            holder.addSetView(setNumber, data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}