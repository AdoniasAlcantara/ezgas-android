package io.github.adoniasalcantara.ezgas.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import io.github.adoniasalcantara.ezgas.R

fun startDirections(context: Context, latitude: Double, longitude: Double) {
    val uri = context.getString(R.string.link_direction, latitude, longitude).toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)

    context.startActivity(intent)
}