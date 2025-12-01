package com.brainstorm.unopad.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.brainstorm.unopad.R
import com.brainstorm.unopad.databinding.FragmentEditBinding
import com.brainstorm.unopad.ui.hideSoftKeyboard
import com.brainstorm.unopad.ui.placeCursorAtEnd
import com.brainstorm.unopad.ui.viewModels
import com.brainstorm.unopad.ui.viewmodel.EditNoteViewModel

class EditNoteFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    private var _binding: FragmentEditBinding? = null
    private val binding: FragmentEditBinding get() = _binding!!

    private val viewModel: EditNoteViewModel by viewModels { EditNoteViewModel.Companion.Factory }

    private lateinit var activityContext: Context
    private var isEditing = false
    private var noteId: Long = -1L

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getLong(ARG_NOTE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNoteById(noteId)
        binding.editNoteTopToolbar.setOnMenuItemClickListener(this)
        binding.editNoteTopToolbar.setNavigationOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            }
        }

        viewModel.note.observe(viewLifecycleOwner) { note ->
            binding.run {
                editNoteTitle.setText(note.title)
                editNoteContent.setText(note.content)
                editNoteDate.setText(note.lastModified)
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> binding.run {
                isEditing = !isEditing
                toggleEditMode(editNoteTitle)
                toggleEditMode(editNoteContent)
                managerKeyboard(editNoteContent)
                return true
            }

            R.id.action_save -> binding.run {
                val title = editNoteTitle.text.toString()
                val content = editNoteContent.text.toString()
                viewModel.updateNote(noteId, title, content)
                hideSoftKeyboard(activityContext, editNoteContent)
                return true
            }

            else -> false
        }
    }


    fun toggleEditMode(editText: EditText) {
        editText.isEnabled = isEditing
        editText.isFocusable = isEditing
        editText.isFocusableInTouchMode = isEditing
    }

    fun managerKeyboard(editText: EditText) {
        if (isEditing) {
            placeCursorAtEnd(activityContext, editText)
        } else {
            // hide keyboard when done
            hideSoftKeyboard(activityContext, editText)
        }
    }

//    private fun placeCursorAtEnd(editText: EditText) {
//        editText.requestFocus()
//        editText.setSelection(editText.text.length) // place cursor at end
//        val imm = activityContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
//    }
//
//    private fun hideSoftKeyboard(editText: EditText) {
//        val imm = activityContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(editText.windowToken, 0)
//    }

    companion object {
        const val TAG = "EditNoteFragment"
        const val ARG_NOTE_ID = "note_id"

        @JvmStatic
        fun newInstance(noteId: Long) = EditNoteFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_NOTE_ID, noteId)
            }
        }
    }
}