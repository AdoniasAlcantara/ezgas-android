package io.github.adoniasalcantara.ezgas.ui.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class DeferredMarker(private val marker: Marker, private val markerSize: Int) : Target {

    init {
        // Tie the marker to this target object in order to prevent
        // this object from being garbage improperly garbage collected
        marker.tag = this
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        placeHolderDrawable ?: return
        val bitmap = placeHolderDrawable.toBitmap(markerSize, markerSize)
        val iconDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
        marker.setIcon(iconDescriptor)
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        val resizedBitmap = bitmap.scale(markerSize, markerSize)
        val descriptor = BitmapDescriptorFactory.fromBitmap(resizedBitmap)
        marker.setIcon(descriptor)
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
}