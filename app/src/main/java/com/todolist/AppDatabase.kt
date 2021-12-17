package com.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.todolist.TaskDao
import com.todolist.AppDatabase
import androidx.room.Room

@Database(version = 1, exportSchema = false, entities = [Task::class])

abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var appDatabase: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase? {
            if (appDatabase == null) appDatabase =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "db_app")
                    .allowMainThreadQueries()
                    .build()
            return appDatabase
        }
    }

    abstract fun taskDao(): TaskDao
}