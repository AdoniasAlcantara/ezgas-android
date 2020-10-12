package io.github.adoniasalcantara.ezgas.ui.stations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationLoadingBinding

class LoadAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val binding = LayoutStationLoadingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}