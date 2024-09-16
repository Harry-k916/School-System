package com.example.mobileapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.ui.theme.screens.SplashScreen
import com.example.mobileapp.ui.theme.screens.home.DashboardScreen
import com.example.mobileapp.ui.theme.screens.login.LoginScreen
import com.example.mobileapp.ui.theme.screens.register.Greeting
import com.example.mobileapp.ui.theme.screens.student.Student
import com.example.mobileapp.ui.theme.screens.student.UpdateStudent
import com.example.mobileapp.ui.theme.screens.student.ViewStudentsScreen

@Composable
fun AppNavHost(
    navController: NavHostController= rememberNavController(),
    startDestination: String= ROUTE_DASHBOARD
){
    NavHost(navController = navController, startDestination=startDestination){
        composable(ROUTE_REGISTER){ Greeting(navController)}
        composable(ROUTE_LOGIN){ LoginScreen(navController)}
        composable(ROUTE_SPLASH){ SplashScreen(navController) }
        composable(ROUTE_DASHBOARD){ DashboardScreen(navController)}
        composable(ROUTE_ADD_STUDENT){ Student(navController)}
        composable(ROUTE_VIEW_STUDENT){ViewStudentsScreen(navController)}
        composable(ROUTE_UPDATE_STUDENT+"/{id}"){
            passedData -> UpdateStudent(
            navController,passedData.arguments?.getString("id")!! )
        }
    }


}