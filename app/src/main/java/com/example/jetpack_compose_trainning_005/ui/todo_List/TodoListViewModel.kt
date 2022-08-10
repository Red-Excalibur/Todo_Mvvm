package com.example.jetpack_compose_trainning_005.ui.todo_List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_trainning_005.data.Todo
import com.example.jetpack_compose_trainning_005.data.repository.TodoRepository
import com.example.jetpack_compose_trainning_005.util.Routes
import com.example.jetpack_compose_trainning_005.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.annotation.meta.When
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
         private val repository: TodoRepository
) : ViewModel() {

    //that return a flow we can easily collect as state later
    //data state
    val todos = repository.getTodos()

    //events
     private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    //here we save the to-do we deleted in case we wanna retrieve
    private var deletedTodo:Todo?=null

    //here are events from the Ui to VM
    fun onEvent(event:TodoListEvent){
        when(event){
            is TodoListEvent.OnTodoClick -> {
                //here we need to get the to-do id to show it
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO+"?todoId=${event.todo.id}"))
                }
            is TodoListEvent.OnAddClick-> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
                   }
            is TodoListEvent.OnUndoDeleteClick -> {
                deletedTodo?.let { todo->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }

                }
            }

            is TodoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo=event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = " Todo deleted",
                        action = "Undo"
                    ))

                }
            }
            is TodoListEvent.OnDoneChange ->{
                //here we change the data base and update
                //we insert and since they heve the same id
                //the onConflict strategy will just update
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }
    //here are events from the Vm to Ui
    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}