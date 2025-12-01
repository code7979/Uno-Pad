package com.brainstorm.unopad.domain

import com.brainstorm.unopad.data.Note
import com.brainstorm.unopad.data.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepository(private val notesDao: NotesDao) {

    suspend fun insertNote(note: Note) = withContext(Dispatchers.IO) {
        notesDao.insert(note)
    }

    suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        notesDao.update(note)
    }

    suspend fun updateNote(
        id: Long,
        title: String,
        content: String,
        lastModified: Long,
    ) = withContext(Dispatchers.IO) {
        notesDao.update(id, title, content, lastModified)
    }

    suspend fun deleteNote(noteId: Long) = withContext(Dispatchers.IO) {
        notesDao.delete(noteId)
    }

    suspend fun deleteNotes(ids: List<Long>) = withContext(Dispatchers.IO) {
        notesDao.deleteNotes(ids)
    }

    suspend fun getNoteById(noteId: Long) = withContext(Dispatchers.IO) {
        notesDao.getNoteById(noteId)
    }


    fun getAllNote(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

}