package com.example.jetpack_compose_trainning_005.ui.todo_List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpack_compose_trainning_005.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun TodoListScreen(
    onNavigate:(UiEvent.Navigate)-> Unit,
    viewModel: TodoListViewModel= hiltViewModel()
){
    //for Snack bars and Ui material :
    val scaffoldState= rememberScaffoldState()
    //to be able to update..when it's changed in the Db we recompose
    //so here we are collecting the data
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    //now we need to collect the events
    LaunchedEffect(key1 = true ){
        //that will execute this code here independently of this composable function
        //cuz this function is called and recomposed every single time our todos data update
        //and we don't want to subscribe to our ui event flow every time the ui updates
        //we just actually want to do it once when we show it the first time
        //and that's what that launch effect block will take care of
        viewModel.uiEvent.collect{event->
           //every time we use the onEvent fun in the viewModel we trigger this bloc of code
            when(event){
                is UiEvent.ShowSnackBar -> {
                  val result=  scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                       actionLabel= event.action
                    )
                    if(result==SnackbarResult.ActionPerformed){
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> {onNavigate(event)}
                else -> Unit

            }
        }

    }
    Scaffold(
        scaffoldState=scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TodoListEvent.OnAddClick)
            }) {
                 Icon(imageVector = Icons.Default.Add,
                     contentDescription = "ADD")
            }
        }
    ) {
           LazyColumn(
               modifier = Modifier.fillMaxSize()
           ){
               items(todos.value){todo->
                   TodoItem(todo =todo ,
                       onEvent =viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
                            }
                            .padding(16.dp)
                       )

               }
           }
    }
}
//we used value to get the actuall list
//look how we passed on event function to the child composables (they don't have the view model instance
//but they got access to the function
//we just delegate this onEvent