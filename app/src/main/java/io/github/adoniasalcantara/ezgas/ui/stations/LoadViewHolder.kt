package io.github.adoniasalcantara.ezgas.ui.stations

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationLoadingBinding
import retrofit2.HttpException
import java.io.IOException

class LoadViewHolder(private val binding: LayoutStationLoadingBinding, retry: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    init {
        // Fill half the window with placeholders
        val placeholderHeight = context.resources.getDimensionPixelSize(R.dimen.card_height)
        val halfWindowHeight = context.resources.displayMetrics.heightPixels / 2
        val placeholders = halfWindowHeight / placeholderHeight
        val inflater = LayoutInflater.from(context)

        repeat(placeholders) {
            inflater.inflate(R.layout.layout_station_placeholder, binding.placeholders, true)
        }

        binding.retry.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        binding.placeholders.isVisible = loadState is LoadState.Loading
        binding.errorGroup.isVisible = loadState is LoadState.Error

        if (loadState is LoadState.Error) {
            binding.errorMessage.text = when (val error = loadState.error) {
                is HttpException -> context.getString(R.string.error_code, error.code())
                is IOException -> context.getString(R.string.error_network_message)
                else -> context.getString(R.string.error_unexpected)
            }
        }
    }
}