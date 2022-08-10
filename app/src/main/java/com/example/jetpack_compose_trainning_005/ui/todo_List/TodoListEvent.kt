package com.example.jetpack_compose_trainning_005.ui.todo_List

import com.example.jetpack_compose_trainning_005.data.Todo

sealed class TodoListEvent {
    //we write the info we need from every event here
    data class OnDeleteTodoClick(val todo: Todo):TodoListEvent()
    data class OnDoneChange(val todo: Todo,val isDone:Boolean ):TodoListEvent()
    object OnUndoDeleteClick :TodoListEvent()
    data class OnTodoClick(val todo: Todo):TodoListEvent()
    object OnAddClick :TodoListEvent()

    //object are the ones that have no parameters



}

