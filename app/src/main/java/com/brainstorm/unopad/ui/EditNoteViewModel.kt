package com.brainstorm.unopad.ui

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.brainstorm.unopad.UnoPadApp
import com.brainstorm.unopad.domain.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditNoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _note = MutableLiveData<NoteItem>()
    val note: LiveData<NoteItem> get() = _note


    fun getNoteById(noteId: Long) {
        if (noteId == -1L) return
        viewModelScope.launch(Dispatchers.IO) {
            val note = noteRepository.getNoteById(noteId)
            if (note != null) {
                val noteItem = NoteItem(
                    id = note.id,
                    isSelected = false,
                    title = note.title,
                    content = note.content,
                    lastModified = note.lastModified.toReadableDate()
                )
                _note.postValue(noteItem)
            }
        }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch {
            val currentTimeMillis = System.currentTimeMillis()
            noteRepository.updateNote(id, title, content, currentTimeMillis)
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as UnoPadApp
                val repository = application.appContainer.repository
                return EditNoteViewModel(repository) as T
            }
        }

        fun Long.toReadableDate(): String {
            val date = Date(this)
            val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            return format.format(date)
        }

    }
}