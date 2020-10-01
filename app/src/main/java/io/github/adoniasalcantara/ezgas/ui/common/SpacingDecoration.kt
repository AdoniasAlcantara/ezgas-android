package io.github.adoniasalcantara.ezgas.ui.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class SpacingDecoration(context: Context, @DimenRes spacing: Int) : RecyclerView.ItemDecoration() {

    private val spacing = context.resources.getDimensionPixelSize(spacing)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            top = if (parent.getChildAdapterPosition(view) == 0) spacing else 0
            bottom = spacing
        }
    }
}