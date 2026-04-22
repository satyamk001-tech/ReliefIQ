package com.reliefiq.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.model.Task
import com.reliefiq.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<Resource<List<Task>>>(Resource.Loading)
    val tasks: StateFlow<Resource<List<Task>>> = _tasks.asStateFlow()

    init {
        loadMapData()
    }

    private fun loadMapData() {
        viewModelScope.launch {
            taskRepository.getActiveTasks().collect { result ->
                _tasks.value = result
            }
        }
    }
}
