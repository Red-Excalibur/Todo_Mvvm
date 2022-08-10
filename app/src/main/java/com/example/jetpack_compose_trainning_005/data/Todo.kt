package com.example.jetpack_compose_trainning_005.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title :String,
    val description:String?=null,
    val isDone:Boolean,
    @PrimaryKey
    val id:Int?=null
)
