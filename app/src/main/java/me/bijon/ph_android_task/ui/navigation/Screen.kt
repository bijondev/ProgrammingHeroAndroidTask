package me.bijon.ph_android_task.ui.navigation

sealed class Screen(val route: String) {
    object CourseList : Screen("course_list")
    object CourseDetail : Screen("course_detail/{courseId}") {
        fun createRoute(courseId: String) = "course_detail/$courseId"
    }
}
