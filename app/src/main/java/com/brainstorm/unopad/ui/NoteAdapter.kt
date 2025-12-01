package com.brainstorm.unopad.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.brainstorm.unopad.R
import com.brainstorm.unopad.databinding.NoteItemBinding


class NoteAdapter(
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes: List<NoteItem> = emptyList()

    private var _layoutInflater: LayoutInflater? = null

    private var isActionMode: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    fun setNoteItems(value: List<NoteItem>) {
        notes = value
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setActionModeEnable(isEnable: Boolean) {
        isActionMode = isEnable
        notifyDataSetChanged()
    }

    fun getNoteItem(position: Int): NoteItem? {
        val size = notes.size
        return if (position in 0 until size) notes[position] else null
    }


    fun requireNoteItem(position: Int): NoteItem {
        try {
            return notes[position]
        } catch (cause: IndexOutOfBoundsException) {
            throw RuntimeException("Provided position is not valid", cause)
        }
    }

    fun setSelectNoteItem(position: Int, isSelect: Boolean) {
        val noteItem = getNoteItem(position)
        if (noteItem != null) {
            noteItem.isSelected = isSelect
            notifyItemChanged(position)
        }
    }

    fun clearSelection(selectionSet: Set<Long>) {
        notes.forEachIndexed { index, note ->
            if (selectionSet.contains(note.id) && note.isSelected) {
                note.isSelected = false
                notifyItemChanged(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = _layoutInflater ?: LayoutInflater.from(parent.context).also {
            _layoutInflater = it  // This saves it to the field
        }
        val binding: NoteItemBinding = NoteItemBinding.inflate(layoutInflater, parent, false)
        return NoteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note, isActionMode)
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(
        private val binding: NoteItemBinding,
        private val listener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, View.OnClickListener {

        init {
            binding.noteCard.setOnClickListener(this)
            binding.noteCard.setOnLongClickListener(this)
            binding.ivNoteSelectionBox.setOnClickListener(this)
        }

        override fun onLongClick(view: View): Boolean {
            listener.onLongClick(view, absoluteAdapterPosition)
            return true
        }

        override fun onClick(view: View) {
            listener.onItemClick(view, absoluteAdapterPosition)
        }

        fun bind(note: NoteItem, isActionMode: Boolean) {
            binding.run {
                val resId = if (note.isSelected) R.drawable.ic_check else 0
                ivNoteSelectionBox.setImageResource(resId)
                selectionLayout.isVisible = isActionMode
                tvRelativeTime.text = note.lastModified
                tvNoteContent.text = note.content
                tvNoteTitle.text = note.title
            }
        }
    }

}