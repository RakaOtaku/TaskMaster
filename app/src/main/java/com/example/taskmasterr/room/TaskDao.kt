package com.example.taskmasterr.room

import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    suspend fun addTask(movie: Task)

    @Update
    suspend fun updateTask(movie: Task)

    @Delete
    suspend fun deleteTask(movie: Task)

    @Query("SELECT * FROM task")
    suspend fun getTask(): List<Task>

    @Query("SELECT * FROM task WHERE id=:movie_id")
    suspend fun getTask(movie_id: Int): List<Task>

}
