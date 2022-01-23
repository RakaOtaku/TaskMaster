package com.example.taskmasterr.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDb : RoomDatabase(){

    abstract fun TaskDao() : TaskDao

    companion object {

        @Volatile private var instance : TaskDb? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TaskDb::class.java,
            "movie12345db"
        ).build()

    }

}