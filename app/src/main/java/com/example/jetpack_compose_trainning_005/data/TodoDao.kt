package com.example.jetpack_compose_trainning_005.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.concurrent.Flow

@Dao
interface TodoDao {

     @Insert
    suspend fun insertTodo(todo: Todo)

     @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE id=:id")
    suspend fun getTodoById(id:Int): Todo?

    @Query("SELECT * FROM todo")
     fun getTodos(): kotlinx.coroutines.flow.Flow<List<Todo>>
}