package com.example.jetpack_compose_trainning_005.di

import android.app.Application
import androidx.room.Room
import com.example.jetpack_compose_trainning_005.data.TodoDataBase
import com.example.jetpack_compose_trainning_005.data.repository.TodoRepository
import com.example.jetpack_compose_trainning_005.data.repository.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //here u tell dagger hilt how it build the instances:

    @Provides
    @Singleton
    fun provideTodoDataBase(app:Application): TodoDataBase{
        return Room.databaseBuilder(
            app,
            TodoDataBase::class.java,
            "todo_db"
        ).build()
    }
    //here the repository ...notice that we are injecting the Repo-Interface...
    //and we are implementing it with its implementation we chose
    @Provides
    @Singleton
    fun provideTodoRepository(db:TodoDataBase):TodoRepository {
     return TodoRepositoryImpl(db.dao)
    }


    //after this dagger know how to create repoand db so we can simply inject them..
}