package com.brainstorm.unopad.di

import androidx.room.Room
import android.app.Application
import com.brainstorm.unopad.data.AppDatabase
import com.brainstorm.unopad.domain.NoteRepository

class AppContainer(application: Application) {
    private val _database = Room.databaseBuilder(
        klass = AppDatabase::class.java,
        context = application,
        name = "db_unopad"
    ).build()


    val repository: NoteRepository = NoteRepository(_database.notesDao())

}