package com.brainstorm.unopad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.brainstorm.unopad.R
import com.brainstorm.unopad.databinding.FragmentAddBinding

class AddNoteFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding get() = _binding!!

    private val viewmodel: NotesViewModel by activityViewModels { NotesViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNoteTopToolbar.setOnMenuItemClickListener(this)
        binding.addNoteTopToolbar.setNavigationOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> binding.run {
                val title = addNoteTitle.text.toString()
                val content = addNoteContent.text.toString()
                viewmodel.saveNote(title, content)
                return true
            }

            else -> false
        }
    }

    companion object {
        const val TAG = "EditNoteFragment"
    }

}