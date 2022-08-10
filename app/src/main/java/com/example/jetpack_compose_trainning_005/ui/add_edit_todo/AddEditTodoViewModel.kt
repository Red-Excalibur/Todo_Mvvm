package com.example.jetpack_compose_trainning_005.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_trainning_005.data.Todo
import com.example.jetpack_compose_trainning_005.data.repository.TodoRepository
import com.example.jetpack_compose_trainning_005.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//the saved state handle contains our navigation arguments ...hilt will provide it
@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
 private val repository: TodoRepository,
 savedStateHandle: SavedStateHandle
):ViewModel() {
    //we need a state for the textField and description and the To-do we are actually in
    var todo by mutableStateOf<Todo?>(null) // so it's the value directly not x.value
    private set // means we can only change it from the inside but we can read it frm the outside

    var title by mutableStateOf("")
    private set

    var description by mutableStateOf("")
    private set
    //we need also to send events from the Vm  to the ui so :
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    //will be ex-uc-uted as soon as our viewModel is initialised :
    //Also here we wanna check if we opened this page by clicking on an existing to do so we wanna
    //bring it frm the DB and update it ...or from the Add floating btn
    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if(todoId != -1 )
        {
        //means we clicked on an existing one and we need to load it
        viewModelScope.launch {
           repository.getTodoById(todoId)?.let {todo->
               title=todo.title
               description=todo.description ?: ""
               this@AddEditTodoViewModel.todo=todo
           }

        }
        }
    }
    fun onEvent(event:AddEditTodoEvent){
       when(event){
           is AddEditTodoEvent.OnTitleChange ->{
               title=event.title
           }
           is AddEditTodoEvent.OnDescriptionChange ->{
               description=event.description
           }
           is AddEditTodoEvent.OnSaveTodoClick ->{
               viewModelScope.launch {
                   if(title.isBlank()){
                       sendUiEvent(UiEvent.ShowSnackBar(
                           message = "The title can't be empty"
                       ))
                       return@launch
                   }
                   //we construct a new todo and add it
                   repository.insertTodo(
                       Todo(title,
                           description,
                           isDone = todo?.isDone ?:false,
                           id=todo?.id
                       )
                   )
                   sendUiEvent(UiEvent.PopBackStack)
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