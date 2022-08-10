package com.example.jetpack_compose_trainning_005

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpack_compose_trainning_005.ui.add_edit_todo.AddEditTodoScreen
import com.example.jetpack_compose_trainning_005.ui.theme.Jetpack_Compose_trainning_005Theme
import com.example.jetpack_compose_trainning_005.ui.todo_List.TodoListScreen
import com.example.jetpack_compose_trainning_005.util.Routes
import com.example.jetpack_compose_trainning_005.util.Routes.TODO_LIST
import dagger.hilt.android.AndroidEntryPoint

//to inject every thing in the activity
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_Compose_trainning_005Theme {
               val navController= rememberNavController()

                NavHost(navController = navController,
                    startDestination =Routes.TODO_LIST  ){
                       //our screens
                    composable(Routes.TODO_LIST){
                        TodoListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }

                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId"){
                                type= NavType.IntType
                                defaultValue=-1
                            }
                        )
                    ) {
                        AddEditTodoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}
