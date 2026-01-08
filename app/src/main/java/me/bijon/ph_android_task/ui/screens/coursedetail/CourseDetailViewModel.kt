package me.bijon.ph_android_task.ui.screens.coursedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.bijon.ph_android_task.domain.model.Course
import me.bijon.ph_android_task.domain.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val repository: CourseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val courseId: String = checkNotNull(savedStateHandle["courseId"])

    val course: StateFlow<Course?> = repository.getCourseById(courseId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun toggleEnrollment() {
        val currentCourse = course.value ?: return
        viewModelScope.launch {
            repository.updateEnrollment(currentCourse.id, !currentCourse.isEnrolled)
        }
    }
}
