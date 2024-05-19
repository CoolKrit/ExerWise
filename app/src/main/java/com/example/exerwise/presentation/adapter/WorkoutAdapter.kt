
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exerwise.data.model.Workout
import com.example.exerwise.databinding.ItemWorkoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutAdapter(private var optionsMenuClickListener: OptionsMenuClickListener, private val startButtonClickListener: (Workout) -> Unit) :
    ListAdapter<Workout, WorkoutAdapter.WorkoutViewHolder>(WorkoutDiffCallback()) {

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int, workout: Workout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WorkoutViewHolder(private val binding: ItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            binding.workoutName.text = workout.name
            binding.workoutMoreMenu.setOnClickListener {
                optionsMenuClickListener.onOptionsMenuClicked(position, workout)
            }
            binding.workoutStart.setOnClickListener {
                startButtonClickListener(workout)
            }
        }
    }

    class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }
    }
}