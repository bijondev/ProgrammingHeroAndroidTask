package me.bijon.ph_android_task.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.bijon.ph_android_task.ui.screens.coursedetail.CourseDetailScreen
import me.bijon.ph_android_task.ui.screens.courselist.CourseListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.CourseList.route) {
        composable(Screen.CourseList.route) {
            CourseListScreen(
                onCourseClick = { courseId ->
                    navController.navigate(Screen.CourseDetail.createRoute(courseId))
                }
            )
        }
        composable(
            route = Screen.CourseDetail.route,
            arguments = listOf(navArgument("courseId") { type = NavType.StringType })
        ) {
            CourseDetailScreen(navController = navController)
        }
    }
}
