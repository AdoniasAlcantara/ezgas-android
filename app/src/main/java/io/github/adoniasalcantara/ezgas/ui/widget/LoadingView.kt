package io.github.adoniasalcantara.ezgas.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import io.github.adoniasalcantara.ezgas.databinding.ViewLoadingBinding

class LoadingView : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private val binding = ViewLoadingBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun showLoading() {
        binding.apply {
            errorGroup.isVisible = false
            retry.isVisible = false
            loadingIndicator.isVisible = true
        }

        show()
    }

    fun showError(builder: ErrorEntry.Builder.() -> Unit = {}) {
        val errorEntry = ErrorEntry.Builder(context).apply(builder).build()

        binding.apply {
            loadingIndicator.isVisible = false

            errorGroup.isVisible = true
            errorImage.setImageDrawable(errorEntry.image)
            errorTitle.text = errorEntry.title
            errorMessage.text = errorEntry.message

            retry.isVisible = errorEntry.retryCallback != null
            retry.text = errorEntry.retryText
            retry.setOnClickListener { errorEntry.retryCallback?.invoke() }
        }

        show()
    }

    fun hide() {
        animate()
            .setDuration(ANIMATION_DURATION)
            .alpha(0f)
            .withEndAction { isVisible = false }

    }

    private fun show() {
        animate()
            .setDuration(ANIMATION_DURATION)
            .alpha(1f)
            .withStartAction { isVisible = true }
    }

    private companion object {
        const val ANIMATION_DURATION = 250L // milliseconds
    }
}