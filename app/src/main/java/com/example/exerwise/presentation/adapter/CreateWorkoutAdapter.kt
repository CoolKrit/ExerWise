package com.example.exerwise.presentation.adapter

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.exerwise.R
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.ExerciseSet
import com.example.exerwise.databinding.ItemExerciseWorkoutBinding
import com.google.android.material.card.MaterialCardView

class CreateWorkoutAdapter(private val context: Context, private var data: MutableList<Exercise>) : RecyclerView.Adapter<CreateWorkoutAdapter.ViewHolder>() {

    private val restTimeValues = listOf(30, 60, 90, 120, 150, 180, 210, 240, 270, 300)
    private val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, restTimeValues)
    private var mediaPlayer: MediaPlayer? = null

    private var restTimer: CountDownTimer? = null
    private var exerciseRestTimeSeconds: Long = 0

    inner class ViewHolder(binding: ItemExerciseWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.exerciseTitle
        val exerciseSetsContainer: LinearLayout = binding.exerciseSetsContainer
        val setButton: Button = binding.exerciseAddSet
        val deleteButton: ImageButton = binding.exerciseDeleteButton
        val exerciseInstructions: TextView = binding.exerciseInstructions
        var exerciseRestSpinner: Spinner = binding.exerciseRestSpinner
        var exerciseRestTimer: TextView = binding.exerciseRestTimer

        fun addSetView(setNumber: Int, exercise: Exercise) {
            val setView = LayoutInflater.from(itemView.context).inflate(R.layout.item_exercise_set, exerciseSetsContainer, false)
            val textSetNumber: TextView = setView.findViewById(R.id.setNumberTextView)
            val editWeight: EditText = setView.findViewById(R.id.weightEditText)
            val editReps: EditText = setView.findViewById(R.id.repsEditText)
            val deleteSetButton: ImageButton = setView.findViewById(R.id.deleteSetButton)

            textSetNumber.text = "$setNumber"

            setView.setOnClickListener {
                val selectedRestTimeSeconds = exerciseRestSpinner.selectedItem as Int
                val exerciseSetCardView: MaterialCardView = setView.findViewById(R.id.exersiceSetCardView)
                exerciseSetCardView.setCardBackgroundColor(context.getColor(R.color.holo_green))
                startRestTimer(selectedRestTimeSeconds.toLong())
            }

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
                }
            }

            // Слушатель изменений в поле повторений
            editReps.addTextChangedListener {
                val repsText = it.toString()
                if (repsText.isNotEmpty()) {
                    exercise.sets.find { it.setNumber == setNumber }?.reps = repsText
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

        fun startRestTimer(restTimeSeconds: Long) {
            exerciseRestTimeSeconds = restTimeSeconds

            // Создание и запуск таймера обратного отсчёта
            restTimer?.cancel() // отменить предыдущий таймер, если он был запущен
            restTimer = object : CountDownTimer(restTimeSeconds * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val minutes = millisUntilFinished / 1000 / 60
                    val seconds = (millisUntilFinished / 1000) % 60
                    exerciseRestTimer.text = String.format("%02d:%02d", minutes, seconds)
                }

                override fun onFinish() {
                    exerciseRestTimer.text = "00:00"
                    playNotificationSound()
                }
            }.start()
        }

        fun playNotificationSound() {
            mediaPlayer?.start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position].name
        holder.exerciseSetsContainer.removeAllViews()

        holder.exerciseRestSpinner.adapter = adapter
        mediaPlayer = MediaPlayer.create(context, R.raw.notifications)

        holder.exerciseInstructions.text = data[position].instructions

        data[position].sets.forEach { set ->
            holder.addSetView(set.setNumber, data[position])
        }

        holder.deleteButton.setOnClickListener {
            data.remove(data[position])
            notifyDataSetChanged()
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

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
