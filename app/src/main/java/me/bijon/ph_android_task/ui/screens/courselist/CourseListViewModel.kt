package me.bijon.ph_android_task.ui.screens.courselist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.bijon.ph_android_task.domain.model.Course
import me.bijon.ph_android_task.domain.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CourseListViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    val courses: StateFlow<List<Course>> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getAllCourses()
            } else {
                repository.searchCourses(query)
            }
        }
        .onEach { list ->
            android.util.Log.d("CourseListVM", "Emitting ${list.size} courses to UI")
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        refresh()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            repository.refreshCourses()
            _isRefreshing.value = false
        }
    }
}
