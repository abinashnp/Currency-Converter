package com.abinash.currencyconverter.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import android.graphics.Rect
import android.view.View

/**
 * Used for adding paddings to recyclerview items
 */
class ListItemPadding(private val space: Int, private val lastSpace: Boolean) :
    ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = 0
        outRect.right = 0
        outRect.bottom = space
        outRect.top = 0
        if (lastSpace) {
            if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
                outRect.bottom = 100 /* set your margin here */
            }
        }
    }
}