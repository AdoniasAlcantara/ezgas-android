package io.github.adoniasalcantara.ezgas.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import io.github.adoniasalcantara.ezgas.R

class ErrorEntry private constructor(
    val image: Drawable,
    val title: CharSequence,
    val message: CharSequence?,
    val retryCallback: (() -> Unit)?,
    val retryText: CharSequence
) {

    class Builder(private val context: Context) {

        var image: Drawable? = null
        var title: CharSequence? = null
        var message: CharSequence? = null
        var retryCallback: (() -> Unit)? = null
        var retryText: CharSequence? = null

        fun image(@DrawableRes image: Int) {
            this.image = ContextCompat.getDrawable(context, image)
        }

        fun title(@StringRes title: Int) {
            this.title = context.getString(title)
        }

        fun message(@StringRes message: Int) {
            this.message = context.getString(message)
        }

        fun retryText(@StringRes retryText: Int) {
            this.retryText = context.getString(retryText)
        }

        fun build() = ErrorEntry(
            image ?: ContextCompat.getDrawable(context, R.drawable.ic_close)!!,
            title ?: context.getString(R.string.error),
            message,
            retryCallback,
            retryText ?: context.getText(R.string.error_retry)
        )
    }
}