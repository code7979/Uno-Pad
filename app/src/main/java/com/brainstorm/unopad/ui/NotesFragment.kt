package com.brainstorm.unopad.ui

import android.content.Context
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brainstorm.unopad.R
import com.brainstorm.unopad.databinding.FragmentListBinding


class NotesFragment : Fragment(), View.OnClickListener, OnItemClickListener, ActionMode.Callback {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = _binding!!

    private var _adapter: NoteAdapter? = null
    private val adapter: NoteAdapter get() = _adapter!!

    private lateinit var activityContext: Context

    private var actionMode: ActionMode? = null

    private val viewModel: NotesViewModel by activityViewModels { NotesViewModel.Factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddNote.setOnClickListener(this)
        setUpAudioRecyclerView()

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.setNoteItems(notes)
        }

        viewModel.selectedItemCount.observe(viewLifecycleOwner) { count ->
            setActionModeTitle(count)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAudioRecyclerView() {
        val recyclerView: RecyclerView = binding.rcViewNotes
        val layoutManager = LinearLayoutManager(activityContext)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setItemAnimator(DefaultItemAnimator())
        this._adapter = NoteAdapter(this)
        recyclerView.setAdapter(adapter)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_add_note -> {
                goToAddFragment()
            }
        }
    }

    private fun goToAddFragment() {
        val fragmentClz = AddNoteFragment::class.java
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragmentClz, null)
            .setReorderingAllowed(true)
            .addToBackStack(AddNoteFragment.TAG)
            .commit()
    }

    override fun onItemClick(view: View, position: Int) {
        val id = view.id
        when (id) {
            R.id.note_card -> {
                val noteItem = adapter.requireNoteItem(position)
                val fragment = EditNoteFragment.newInstance(noteItem.id)
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, fragment)
                    .setReorderingAllowed(true)
                    .addToBackStack(EditNoteFragment.TAG)
                    .commit()
            }

            R.id.iv_note_selection_box -> {
                val noteItem = adapter.getNoteItem(position)
                if (noteItem != null) {
                    noteItem.isSelected = !noteItem.isSelected
                    adapter.notifyItemChanged(position)
                    viewModel.toggleSelection(noteItem.id)
                }
            }
        }

    }

    override fun onLongClick(view: View, position: Int) {
        if (actionMode == null) {
            actionMode = activity?.startActionMode(this)
        }
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        adapter.setActionModeEnable(true)
        mode.menuInflater.inflate(R.menu.selection_menu, menu)
        return true
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                viewModel.deleteNotes()
                mode.finish()
                true
            }

            else -> false
        }
    }


    override fun onDestroyActionMode(mode: ActionMode?) {
        adapter.setActionModeEnable(false)
        val selectionList = viewModel.clearSelection()
        adapter.clearSelection(selectionList)
        actionMode = null
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false


    private fun setActionModeTitle(count: Int) {
        actionMode?.let { mode ->
            val resQStr = R.plurals.items_selected
            val quantityString = resources.getQuantityString(resQStr, count, count)
            mode.title = quantityString
        }
    }

}