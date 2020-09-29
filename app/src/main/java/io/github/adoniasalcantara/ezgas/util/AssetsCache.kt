package io.github.adoniasalcantara.ezgas.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.collection.LruCache

class AssetsCache(private val context: Context, size: Int) {

    private val cache = object : LruCache<String, Bitmap>(size) {

        override fun sizeOf(key: String, value: Bitmap): Int = value.allocationByteCount
    }

    fun getBitmapOrDefault(path: String, @DrawableRes default: Int): Bitmap {
        return cache[path]
            ?: runCatching {
                openFromAssets(path)
            }.getOrElse {
                openDefault(default)
            }.also { bitmap ->
                cache.put(path, bitmap)
            }
    }

    private fun openFromAssets(path: String): Bitmap {
        return context.assets.open(path).use { stream ->
            BitmapFactory.decodeStream(stream)
        }
    }

    private fun openDefault(default: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, default)
    }
}