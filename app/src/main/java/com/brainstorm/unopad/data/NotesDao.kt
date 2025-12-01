package com.brainstorm.unopad.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM notes WHERE id IN (:ids)")
    fun deleteNotes(ids: List<Long>)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Note?

    @Query("UPDATE notes SET title = :title, content = :content, last_modified = :lastModified WHERE id = :id;")
    fun update(id: Long, title: String, content: String, lastModified: Long)

    @Query("SELECT * FROM notes ORDER BY last_modified DESC")
    fun getAllNotes(): Flow<List<Note>>


}