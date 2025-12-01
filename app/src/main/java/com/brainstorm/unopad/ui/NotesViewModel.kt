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
import com.brainstorm.unopad.data.Note
import com.brainstorm.unopad.domain.NoteRepository
import com.brainstorm.unopad.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _notes = MutableLiveData<List<NoteItem>>()
    val notes: LiveData<List<NoteItem>> get() = _notes

    private val selectionSet: MutableSet<Long> = mutableSetOf<Long>()

    private val _selectedItemCount = MutableLiveData<Int>(selectionSet.size)
    val selectedItemCount: LiveData<Int> get() = _selectedItemCount


    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNote().collect { notes ->
                log(this::class.java, notes.toString())
                val noteItems = notes.map {
                    NoteItem(
                        id = it.id,
                        title = it.title,
                        content = it.content,
                        lastModified = getRelativeTime(it.lastModified),
                        isSelected = false
                    )
                }
                _notes.postValue(noteItems)
            }
        }
    }

    fun toggleSelection(noteId: Long) {
        if (selectionSet.contains(noteId)) {
            selectionSet.remove(noteId)
        } else {
            selectionSet.add(noteId)
        }
        _selectedItemCount.postValue(selectionSet.size)
    }


    fun saveNote(title: String, content: String) {
        viewModelScope.launch {
            if (title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(title = title, content = content)
                noteRepository.insertNote(note)
            }
        }
    }



    fun deleteNotes(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(noteId)
        }
    }

    fun deleteNotes() {
        val ids = selectionSet.toList()
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNotes(ids)
        }
        selectionSet.clear()
    }

    /**
     * @return list of selected item that not has been perform action(Delete)
     */
    fun clearSelection(): Set<Long> {
        val temp = selectionSet.toSet()
        selectionSet.clear()
        return temp
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as UnoPadApp
                val repository = application.appContainer.repository
                return NotesViewModel(repository) as T
            }
        }

        fun getRelativeTime(millis: Long): CharSequence {
            return DateUtils.getRelativeTimeSpanString(millis)
        }

    }
}