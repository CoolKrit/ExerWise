package com.example.exerwise.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.exerwise.R
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.databinding.FragmentExerciseListBinding
import com.example.exerwise.presentation.adapter.ExerciseListAdapter
import com.example.exerwise.presentation.api.ExerciseApiClient
import com.example.exerwise.presentation.interfaces.ExerciseListItemClickListener
import com.example.exerwise.presentation.viewmodel.ExerciseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseListFragment : Fragment() {
    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseListAdapter
    private lateinit var itemClickListener: ExerciseListItemClickListener

    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseViewModel =
            ViewModelProvider(requireActivity())[ExerciseViewModel::class.java]

        recyclerView = view.findViewById(R.id.exerciseListRV)
        exerciseAdapter = ExerciseListAdapter(emptyList())
        recyclerView.adapter = exerciseAdapter

        loadExercises("")

        binding.exerciseListSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Обработка изменения текста в поисковой строке
                loadExercises(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        itemClickListener = object : ExerciseListItemClickListener {
            override fun onItemClick(item: Exercise) {
                exerciseViewModel.addExercise(item)
                findNavController().popBackStack()
            }
        }

        exerciseAdapter.setOnItemClickListener(itemClickListener)
    }

    private fun loadExercises(query: String) {
        val apiService = ExerciseApiClient.create()
        apiService.getExercisesByMuscleType("R2eTebiHJNNCB6YqZt1u9w==NoVVNSjUnP3lTNys", query)
            .enqueue(object : Callback<List<Exercise>> {
                override fun onResponse(
                    call: Call<List<Exercise>>,
                    response: Response<List<Exercise>>
                ) {
                    if (response.isSuccessful) {
                        val exercises = response.body() ?: emptyList()
                        exerciseAdapter.updateExercises(exercises)
                    } else {
                    }
                }

                override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                    // Обработка ошибки
                }
            })
    }
}