package com.brainstorm.unopad.ui

data class NoteItem(
    val id: Long = 0L,
    val title: String,
    val content: String,
    val lastModified: CharSequence,
    var isSelected: Boolean,
)

