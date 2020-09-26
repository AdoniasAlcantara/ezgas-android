package io.github.adoniasalcantara.ezgas.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.adoniasalcantara.ezgas.R

abstract class BaseDialogFragment(@LayoutRes private val layout: Int) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layout, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                // Make bottom sheet background round and also apply padding
                window?.findViewById<View>(R.id.design_bottom_sheet)?.let {
                    val paddingTop = resources.getDimensionPixelSize(R.dimen.radius_large)
                    it.setPadding(0, paddingTop, 0, 0)
                    it.setBackgroundResource(R.drawable.bg_bottom_sheet)
                }
            }
        }
    }
}