package io.github.adoniasalcantara.ezgas.ui.stations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationItemBinding
import io.github.adoniasalcantara.ezgas.ui.common.StationViewHolder
import io.github.adoniasalcantara.ezgas.util.AssetsCache

class StationsAdapter(private val assets: AssetsCache) :
    PagingDataAdapter<Station, StationViewHolder>(StationDiffer()) {

    lateinit var fuelType: FuelType
    lateinit var onStationClick: (Station) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binding = LayoutStationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return StationViewHolder(binding, assets, onStationClick)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        getItem(position)?.let { station -> holder.bind(station, fuelType) }
    }

    fun isEmpty() = itemCount == 0

    private class StationDiffer : DiffUtil.ItemCallback<Station>() {

        override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
            return oldItem.fuels == newItem.fuels
        }
    }
}