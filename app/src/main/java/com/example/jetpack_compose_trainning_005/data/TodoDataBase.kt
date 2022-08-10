package com.example.jetpack_compose_trainning_005.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDataBase:RoomDatabase() {

    abstract val dao:TodoDao

}