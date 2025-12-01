package com.brainstorm.unopad.ui

import android.view.View

interface OnItemClickListener {
    fun onItemClick(view: View, position: Int)
    fun onLongClick(view: View, position: Int)
}
