package com.example.jetpack_compose_trainning_005.data.repository


import com.example.jetpack_compose_trainning_005.data.Todo

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id:Int): Todo?

     fun getTodos(): kotlinx.coroutines.flow.Flow<List<Todo>>
}