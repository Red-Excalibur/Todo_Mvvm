package com.example.jetpack_compose_trainning_005.data.repository

import com.example.jetpack_compose_trainning_005.data.Todo
import com.example.jetpack_compose_trainning_005.data.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dao:TodoDao
) : TodoRepository {

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override  fun getTodos(): Flow<List<Todo>> {
       return dao.getTodos()
    }

}